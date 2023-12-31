package com.dadada.onecloset.domain.codi.service;

import com.dadada.onecloset.domain.clothes.entity.Clothes;
import com.dadada.onecloset.domain.clothes.repository.ClothesRepository;
import com.dadada.onecloset.domain.codi.dto.request.CodiDateUpdateRequestDto;
import com.dadada.onecloset.domain.codi.dto.request.CodiRegistRequestDto;
import com.dadada.onecloset.domain.codi.dto.request.CodiUpdateRequestDto;
import com.dadada.onecloset.domain.codi.entity.Codi;
import com.dadada.onecloset.domain.codi.entity.CodiClothes;
import com.dadada.onecloset.domain.codi.repository.CodiClothesRepository;
import com.dadada.onecloset.domain.codi.repository.CodiRepository;
import com.dadada.onecloset.domain.user.entity.User;
import com.dadada.onecloset.domain.user.repository.UserRepository;
import com.dadada.onecloset.exception.CustomException;
import com.dadada.onecloset.exception.ExceptionType;
import com.dadada.onecloset.global.CommonResponse;
import com.dadada.onecloset.global.DataResponse;
import com.dadada.onecloset.global.S3Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CodiService {

    private final UserRepository userRepository;
    private final CodiRepository codiRepository;
    private final ClothesRepository clothesRepository;
    private final CodiClothesRepository codiClothesRepository;
    private final S3Service s3Service;

    @Transactional
    public DataResponse<Long> registCodi(CodiRegistRequestDto requestDto, Long userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        String url = s3Service.upload(requestDto.getImage());

        ObjectMapper objectMapper = new ObjectMapper();
        CodiRegistRequestDto.CodiInfoDto codiInfo = objectMapper.readValue(requestDto.getInfo(), CodiRegistRequestDto.CodiInfoDto.class);

        String wearingAtDay = codiInfo.getDate();

        Codi codi = Codi
                .builder()
                .user(user)
                .originImg(url)
                .thumnailImg(url)
                .wearingAtDay(wearingAtDay)
                .wearingAtMonth(wearingAtDay.substring(0, 7))
                .build();
        Codi codiSave = codiRepository.save(codi);

        for (Long clothesId: codiInfo.getClothesList()) {
            Clothes clothes = clothesRepository.findByIdAndUser(clothesId, user)
                    .orElseThrow(() -> new CustomException(ExceptionType.CLOTHES_NOT_FOUND));
            CodiClothes codiClothes = CodiClothes
                    .builder()
                    .codi(codiSave)
                    .clothes(clothes)
                    .build();
            codiClothesRepository.save(codiClothes);
        }

        return new DataResponse<>(200,"코디 등록 성공", codiSave.getId());
    }

    @Transactional
    public CommonResponse editCodi(CodiUpdateRequestDto requestDto, Long userId) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CodiUpdateRequestDto.CodiInfoDto codiInfo = objectMapper.readValue(requestDto.getInfo(), CodiUpdateRequestDto.CodiInfoDto.class);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        Codi codi = codiRepository.findByIdAndUser(codiInfo.getCodiId(), user)
                .orElseThrow(() -> new CustomException(ExceptionType.CODI_NOT_FOUND));

        if (!requestDto.getImage().isEmpty()) {
            String url = s3Service.upload(requestDto.getImage());
            codi.editImage(url, url);
        }

        String wearingAtDay = codiInfo.getDate();
        codi.editWearingAt(wearingAtDay, wearingAtDay.substring(0, 7));

        List<CodiClothes> codiClothesList = codiClothesRepository.findByCodi(codi);
        codiClothesRepository.deleteAll(codiClothesList);

        for (Long clothesId: codiInfo.getClothesList()) {
            Clothes clothes = clothesRepository.findByIdAndUser(clothesId, user)
                    .orElseThrow(() -> new CustomException(ExceptionType.CLOTHES_NOT_FOUND));
            CodiClothes codiClothes = CodiClothes
                    .builder()
                    .codi(codi)
                    .clothes(clothes)
                    .build();
            codiClothesRepository.save(codiClothes);
        }

        return new CommonResponse(200,"코디 수정 성공");
    }

    @Transactional
    public CommonResponse editCodiWearingAt(CodiDateUpdateRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        Codi codi = codiRepository.findByIdAndUser(requestDto.getCodiId(), user)
                .orElseThrow(() -> new CustomException(ExceptionType.CODI_NOT_FOUND));
        String wearingAtDay = requestDto.getWearingAt();
        codi.editWearingAt(wearingAtDay.substring(0, 7), wearingAtDay);
        return new CommonResponse(200, "코디 날짜 수정 성공");
    }

    @Transactional
    public CommonResponse deleteCode(Long codiId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        Codi codi = codiRepository.findByIdAndUser(codiId, user)
                .orElseThrow(() -> new CustomException(ExceptionType.CODI_NOT_FOUND));
        codiRepository.delete(codi);
        return new CommonResponse(200, "코디 삭제 성공");
    }

}
