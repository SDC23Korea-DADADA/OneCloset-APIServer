package com.dadada.onecloset.domain.clothes.service;

import com.dadada.onecloset.domain.closet.entity.Closet;
import com.dadada.onecloset.domain.closet.entity.ClosetClothes;
import com.dadada.onecloset.domain.closet.repository.ClosetClothesRepository;
import com.dadada.onecloset.domain.closet.repository.ClosetRepository;
import com.dadada.onecloset.domain.clothes.dto.*;
import com.dadada.onecloset.domain.clothes.entity.Clothes;
import com.dadada.onecloset.domain.clothes.entity.Hashtag;
import com.dadada.onecloset.domain.clothes.entity.Tpo;
import com.dadada.onecloset.domain.clothes.entity.Weather;
import com.dadada.onecloset.domain.clothes.entity.code.Color;
import com.dadada.onecloset.domain.clothes.entity.code.Material;
import com.dadada.onecloset.domain.clothes.entity.code.Type;
import com.dadada.onecloset.domain.clothes.entity.type.TpoType;
import com.dadada.onecloset.domain.clothes.entity.type.WeatherType;
import com.dadada.onecloset.domain.clothes.repository.*;
import com.dadada.onecloset.domain.laundrysolution.entity.ClothesCare;
import com.dadada.onecloset.domain.laundrysolution.repository.ClothesSolutionRepository;
import com.dadada.onecloset.domain.user.entity.User;
import com.dadada.onecloset.domain.user.repository.UserRepository;
import com.dadada.onecloset.exception.CustomException;
import com.dadada.onecloset.exception.ExceptionType;
import com.dadada.onecloset.global.CommonResponse;
import com.dadada.onecloset.global.DataResponse;
import com.dadada.onecloset.global.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClothesService {

    private final UserRepository userRepository;
    private final ClothesRepository clothesRepository;
    private final ClosetRepository closetRepository;
    private final ClosetClothesRepository closetClothesRepository;

    private final ColorRepository colorRepository;
    private final MaterialRepository materialRepository;
    private final TypeRepository typeRepository;
    private final ClothesSolutionRepository clothesSolutionRepository;

    private final WeatherRepository weatherRepository;
    private final TpoRepository tpoRepository;
    private final HashtagRepository hashtagRepository;

    private final S3Service s3Service;

    @Transactional
    public CommonResponse registClothes(ClothesRegistRequestDto requestDto, Long userId) throws Exception {

        // thumnail 이미지는 아직 구현 안함
        String originImgUrl = s3Service.upload(requestDto.getImage());
        User user = userRepository.findByIdWhereStatusIsTrue(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        Color color = colorRepository.findByCode(requestDto.getColor())
                .orElseThrow();
        Type type = typeRepository.findByTypeName(requestDto.getType())
                .orElseThrow();
        Material material = materialRepository.findByMaterialName(requestDto.getMaterial())
                .orElseThrow();

        Clothes clothes = Clothes
                .builder()
                .originImg(originImgUrl)
                .thumnailImg(originImgUrl)
                .description(requestDto.getDescription())
                .user(user)
                .color(color)
                .type(type)
                .material(material)
                .build();

        Clothes saveClothes = clothesRepository.save(clothes);

        for (String weather : requestDto.getWeatherList()) {
            Weather weatherEntity = Weather
                    .builder()
                    .clothes(saveClothes)
                    .weather(WeatherType.fromString(weather))
                    .build();
            weatherRepository.save(weatherEntity);
        }
        for (String tpo : requestDto.getTpoList()) {
            Tpo tpoEntity = Tpo
                    .builder()
                    .clothes(saveClothes)
                    .tpo(TpoType.fromString(tpo))
                    .build();
            tpoRepository.save(tpoEntity);
        }
        for (String hashtag : requestDto.getHashtagList()) {
            Hashtag hashtagEntity = Hashtag
                    .builder()
                    .user(user)
                    .clothes(clothes)
                    .hashtag(hashtag)
                    .build();
            hashtagRepository.save(hashtagEntity);
        }

        return new CommonResponse(200, "의류 등록 성공");
    }

    public DataResponse<ClothesAnalyzeResponseDto> analyzeClothes(MultipartFile multipartFile) throws IOException {
        // AI서버로 전송하여 배경제거한 이미지, 재질, 종류, 색상 받기
        FastAPIClothesAnalyzeResponseDto fastAPIresponseDto = new FastAPIClothesAnalyzeResponseDto(multipartFile,"바지","파랑","데님");

        Color color = colorRepository.findByColorName(fastAPIresponseDto.getColor()).orElseThrow();
        Type type = typeRepository.findByTypeName(fastAPIresponseDto.getType()).orElseThrow();
        Material material = materialRepository.findByMaterialName(fastAPIresponseDto.getMaterial()).orElseThrow();
        ClothesCare clothesCare = clothesSolutionRepository.findByMaterialCodeAndTypeCode(material, type).orElseThrow();

        ClothesAnalyzeResponseDto responseDto = ClothesAnalyzeResponseDto.of(fastAPIresponseDto, color, clothesCare);

        return new DataResponse<>(200, "의류 분석 완료", responseDto);
    }

    public DataResponse<Boolean> checkClothes(MultipartFile multipartFile) {
        Boolean isClothes = true;
        // AI서버로 전송하여 의류 여부확인
        return new DataResponse<>(200, "의류 여부 확인", isClothes);
    }

    public DataResponse<List<String>> getHashtagList(Long userId) {
        User user = userRepository.findByIdWhereStatusIsTrue(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        List<String> hashtagList = hashtagRepository.findByUserDistinctHashtagName(user);
        return new DataResponse<>(200, "해시 태그 목록 조회", hashtagList);
    }

    public DataResponse<List<ClothesListResponseDto>> getClothesListInDefaultCloset(Long userId) {
        User user = userRepository.findByIdWhereStatusIsTrue(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));

        List<ClothesListResponseDto> responseDtoList = new ArrayList<>();
        List<Clothes> clothesList = clothesRepository.findByUserWhereIsRegistIsTrue(user);

        for (Clothes clothes: clothesList) {
            responseDtoList.add(ClothesListResponseDto.of(clothes));
        }

        return new DataResponse<>(200, "기본 옷장 조회", responseDtoList);
    }

    public DataResponse<List<ClothesListResponseDto>> getClothesListInCustomCloset(Long closetId, Long userId) {
        User user = userRepository.findByIdWhereStatusIsTrue(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        Closet closet = closetRepository.findByIdAndUser(closetId, user)
                .orElseThrow(() -> new CustomException(ExceptionType.CLOSET_NOT_FOUND));

        List<ClothesListResponseDto> responseDtoList = new ArrayList<>();
        List<ClosetClothes> closetClothesList = closetClothesRepository.findByCloset(closet);

        for (ClosetClothes closetClothes: closetClothesList) {
            responseDtoList.add(ClothesListResponseDto.of(closetClothes.getClothes()));
        }

        return new DataResponse<>(200, "사용자 등록 옷장 조회", responseDtoList);
    }

    public DataResponse<ClothesDetailResponseDto> getClothesDetail(Long clothesId, Long userId) {
        User user = userRepository.findByIdWhereStatusIsTrue(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        Clothes clothes = clothesRepository.findByIdAndUserWhereIsRegistIsTrue(clothesId, user)
                .orElseThrow(() -> new CustomException(ExceptionType.CLOTHES_NOT_FOUND));
        ClothesCare clothesCare = clothesSolutionRepository.findByMaterialCodeAndTypeCode(clothes.getMaterial(), clothes.getType())
                .orElseThrow();

        ClothesDetailResponseDto responseDto = ClothesDetailResponseDto.of(clothes, clothesCare);

        return new DataResponse<>(200, "의류 상세 조회", responseDto);
    }

    @Transactional
    public CommonResponse deleteClothes(Long clothesId, Long userId) {
        User user = userRepository.findByIdWhereStatusIsTrue(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        Clothes clothes = clothesRepository.findByIdAndUserWhereIsRegistIsTrue(clothesId, user)
                .orElseThrow(() -> new CustomException(ExceptionType.CLOTHES_NOT_FOUND));
        clothes.deleteClothes();

        // closetClothesRepository.deleteAll(clothes.getClosetClothesList()); // 이건 삭제 안됨

        List<ClosetClothes> closetClothesList = closetClothesRepository.findByClothes(clothes);
        List<Hashtag> hashtagList = hashtagRepository.findByClothes(clothes);
        List<Weather> weatherList = weatherRepository.findByClothes(clothes);
        List<Tpo> tpoList = tpoRepository.findByClothes(clothes);

        closetClothesRepository.deleteAll(closetClothesList);
        hashtagRepository.deleteAll(hashtagList);
        weatherRepository.deleteAll(weatherList);
        tpoRepository.deleteAll(tpoList);

        return new CommonResponse(200, "의류 삭제 완료");
    }

    public CommonResponse updateClothes(Long clothesId, Long userId) {



        return new CommonResponse(200, "의류 수정 완료");
    }

    @Transactional
    public CommonResponse restoreClothes(Long clothesId, Long userId) {
        User user = userRepository.findByIdWhereStatusIsTrue(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        Clothes clothes = clothesRepository.findByIdAndUser(clothesId, user)
                .orElseThrow(() -> new CustomException(ExceptionType.CLOTHES_NOT_FOUND));
        clothes.restoreClothes();
        return new CommonResponse(200, "의류 복구 성공");
    }

}
