package com.dadada.onecloset.domain.clothes.service;

import com.dadada.onecloset.domain.closet.entity.Closet;
import com.dadada.onecloset.domain.closet.entity.ClosetClothes;
import com.dadada.onecloset.domain.closet.repository.ClosetClothesRepository;
import com.dadada.onecloset.domain.closet.repository.ClosetRepository;
import com.dadada.onecloset.domain.clothes.dto.request.ClothesRegistRequestDto;
import com.dadada.onecloset.domain.clothes.dto.request.ClothesUpdateRequestDto;
import com.dadada.onecloset.domain.clothes.dto.response.ClothesAnalyzeResponseDto;
import com.dadada.onecloset.domain.clothes.dto.response.ClothesDetailResponseDto;
import com.dadada.onecloset.domain.clothes.dto.response.ClothesListResponseDto;
import com.dadada.onecloset.fastapi.FastApiClothesAnalyzeResponseDto;
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
import com.dadada.onecloset.fastapi.FastApiService;
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
    private final FastApiService fastApiService;

    @Transactional
    public DataResponse<Long> registClothes(ClothesRegistRequestDto requestDto, Long userId) throws Exception {

        // thumnail 이미지는 아직 구현 안함
        String originImgUrl = s3Service.upload(requestDto.getImage());
        User user = userRepository.findByIdWhereStatusIsTrue(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        Color color = colorRepository.findByCode(requestDto.getColorCode())
                .orElseThrow(() -> new CustomException(ExceptionType.COLOR_NOT_FOUND));
        Type type = typeRepository.findByTypeName(requestDto.getType())
                .orElseThrow(() -> new CustomException(ExceptionType.TYPE_NOT_FOUND));
        Material material = materialRepository.findByMaterialName(requestDto.getMaterial())
                .orElseThrow(() -> new CustomException(ExceptionType.MATERIAL_NOT_FOUND));

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
        saveWeatherList(saveClothes, requestDto.getWeatherList());
        saveTpoList(saveClothes, requestDto.getTpoList());
        saveHashtagList(saveClothes, requestDto.getHashtagList(), user);

        Closet closet = closetRepository.findByIdAndUser(requestDto.getClosetId(), user)
                .orElseThrow(() -> new CustomException(ExceptionType.CLOSET_NOT_FOUND));

        ClosetClothes closetClothes = ClosetClothes
                .builder()
                .closet(closet)
                .clothes(clothes)
                .build();
        closetClothesRepository.save(closetClothes);

        return new DataResponse<>(200, "의류 등록 성공", saveClothes.getId());
    }

    public DataResponse<ClothesAnalyzeResponseDto> analyzeClothes(MultipartFile multipartFile) throws IOException {
        FastApiClothesAnalyzeResponseDto fastAPIresponseDto = fastApiService.getClothesInfoAndRemoveBackgroundImg(multipartFile);
        Color color = colorRepository.findByColorName(fastAPIresponseDto.getColor())
                .orElseThrow(() -> new CustomException(ExceptionType.COLOR_NOT_FOUND));
        Material material = materialRepository.findByMaterialName(fastAPIresponseDto.getMaterial())
                .orElseThrow(() -> new CustomException(ExceptionType.MATERIAL_NOT_FOUND));
        ClothesCare clothesCare = clothesSolutionRepository.findByMaterialCode(material)
                .orElseThrow();

        ClothesAnalyzeResponseDto responseDto = ClothesAnalyzeResponseDto.of(fastAPIresponseDto, color, clothesCare);

        return new DataResponse<>(200, "의류 분석 완료", responseDto);
    }

    public DataResponse<Boolean> checkClothes(MultipartFile multipartFile) throws IOException {
        Boolean isClothes = fastApiService.isClothes(multipartFile);
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
        ClothesCare clothesCare = clothesSolutionRepository.findByMaterialCode(clothes.getMaterial())
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

        closetClothesRepository.deleteAll(closetClothesRepository.findByClothes(clothes));
        hashtagRepository.deleteAll(hashtagRepository.findByClothes(clothes));
        weatherRepository.deleteAll(weatherRepository.findByClothes(clothes));
        tpoRepository.deleteAll(tpoRepository.findByClothes(clothes));

        return new CommonResponse(200, "의류 삭제 완료");
    }

    @Transactional
    public CommonResponse updateClothes(ClothesUpdateRequestDto requestDto, Long userId) throws IOException {
        // thumnail 이미지는 아직 구현 안함
        User user = userRepository.findByIdWhereStatusIsTrue(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        Clothes clothes = clothesRepository.findByIdAndUserWhereIsRegistIsTrue(requestDto.getClothesId(), user)
                .orElseThrow(() -> new CustomException(ExceptionType.CLOTHES_NOT_FOUND));
        Color color = colorRepository.findByCode(requestDto.getColorCode())
                .orElseThrow(() -> new CustomException(ExceptionType.COLOR_NOT_FOUND));
        Type type = typeRepository.findByTypeName(requestDto.getType())
                .orElseThrow(() -> new CustomException(ExceptionType.TYPE_NOT_FOUND));
        Material material = materialRepository.findByMaterialName(requestDto.getMaterial())
                .orElseThrow(() -> new CustomException(ExceptionType.MATERIAL_NOT_FOUND));

        if (!requestDto.getImage().isEmpty()) {
            String originImgUrl = s3Service.upload(requestDto.getImage());
            clothes.updateUrl(originImgUrl, originImgUrl);
        }

        clothes.updateClothes(requestDto.getDescription(), color, type, material);

        hashtagRepository.deleteAll(hashtagRepository.findByClothes(clothes));
        weatherRepository.deleteAll(weatherRepository.findByClothes(clothes));
        tpoRepository.deleteAll(tpoRepository.findByClothes(clothes));

        saveWeatherList(clothes, requestDto.getWeatherList());
        saveTpoList(clothes, requestDto.getTpoList());
        saveHashtagList(clothes, requestDto.getHashtagList(), user);

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

    @Transactional
    public CommonResponse tempDeleteClothes(Long clothesId, Long userId) {
        User user = userRepository.findByIdWhereStatusIsTrue(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        Clothes clothes = clothesRepository.findByIdAndUser(clothesId, user)
                .orElseThrow(() -> new CustomException(ExceptionType.CLOTHES_NOT_FOUND));
        clothesRepository.delete(clothes);
        return new CommonResponse(200, "의류 삭제 성공");
    }

    public void saveWeatherList(Clothes clothes, List<String> weatherList) {
        for (String weather : weatherList) {
            WeatherType weatherType = WeatherType.fromString(weather);
            Weather weatherEntity = Weather
                    .builder()
                    .clothes(clothes)
                    .weather(WeatherType.fromString(weather))
                    .build();
            if (weatherType != null)
                weatherRepository.save(weatherEntity);
        }
    }

    public void saveTpoList(Clothes clothes, List<String> tpoList) {
        for (String tpo : tpoList) {
            Tpo tpoEntity = Tpo
                    .builder()
                    .clothes(clothes)
                    .tpo(TpoType.fromString(tpo))
                    .build();
            tpoRepository.save(tpoEntity);
        }
    }

    public void saveHashtagList(Clothes clothes, List<String> hashtagList, User user) {
        for (String hashtag : hashtagList) {
            Hashtag hashtagEntity = Hashtag
                    .builder()
                    .user(user)
                    .clothes(clothes)
                    .hashtag(hashtag)
                    .build();
            hashtagRepository.save(hashtagEntity);
        }
    }

}
