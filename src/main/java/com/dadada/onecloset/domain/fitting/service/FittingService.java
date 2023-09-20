package com.dadada.onecloset.domain.fitting.service;

import com.dadada.onecloset.domain.clothes.entity.Clothes;
import com.dadada.onecloset.domain.clothes.repository.ClothesRepository;
import com.dadada.onecloset.domain.fitting.dto.FittingCheckDataDto;
import com.dadada.onecloset.domain.fitting.dto.request.FittingDateUpdateRequestDto;
import com.dadada.onecloset.domain.fitting.dto.request.FittingRequestDto;
import com.dadada.onecloset.domain.fitting.dto.request.FittingSaveRequestDto;
import com.dadada.onecloset.domain.fitting.dto.response.FittingDetailResponseDto;
import com.dadada.onecloset.domain.fitting.dto.response.FittingListResponseDto;
import com.dadada.onecloset.domain.fitting.dto.response.FittingResultResponseDto;
import com.dadada.onecloset.domain.fitting.dto.response.ModelListResponseDto;
import com.dadada.onecloset.domain.fitting.entity.Fitting;
import com.dadada.onecloset.domain.fitting.entity.FittingModel;
import com.dadada.onecloset.domain.fitting.repository.FittingClothesRepository;
import com.dadada.onecloset.domain.fitting.repository.FittingModelRepository;
import com.dadada.onecloset.domain.fitting.repository.FittingRepository;
import com.dadada.onecloset.domain.user.entity.User;
import com.dadada.onecloset.domain.user.repository.UserRepository;
import com.dadada.onecloset.exception.CustomException;
import com.dadada.onecloset.exception.ExceptionType;
import com.dadada.onecloset.fastapi.FastApiFittingRequestDto;
import com.dadada.onecloset.fastapi.FastApiModelRegistResponseDto;
import com.dadada.onecloset.fastapi.FastApiService;
import com.dadada.onecloset.global.CommonResponse;
import com.dadada.onecloset.global.DataResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class FittingService {

    private final FastApiService fastApiService;
    private final UserRepository userRepository;
    private final ClothesRepository clothesRepository;

    private final FittingRepository fittingRepository;
    private final FittingModelRepository fittingModelRepository;
    private final FittingClothesRepository fittingClothesRepository;


    @Transactional
    public CommonResponse registFittingModel(MultipartFile multipartFile, Long userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        FastApiModelRegistResponseDto responseDto = fastApiService.registFittingModel(multipartFile);
        FittingModel fittingModel = FittingModel.builder()
                .user(user)
                .responseDto(responseDto)
                .build();
        fittingModelRepository.save(fittingModel);
        return new CommonResponse(200, "모델이 등록되었습니다.");
    }

    public DataResponse<List<ModelListResponseDto>> getFittingModelList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        List<FittingModel> fittingModelList = fittingModelRepository.findByUserWhereStatusIsTrue(user);
        List<ModelListResponseDto> responseDtoList = new ArrayList<>();
        for (FittingModel fittingModel : fittingModelList) {
            responseDtoList.add(ModelListResponseDto.of(fittingModel));
        }
        return new DataResponse<>(200, "가상 피팅 모델 리스트 조회", responseDtoList);
    }

    @Transactional
    public CommonResponse deleteModel(Long modelId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        FittingModel fittingModel = fittingModelRepository.findByIdAndUserWhereStatusIsTrue(modelId, user)
                .orElseThrow(() -> new CustomException(ExceptionType.MODEL_NOT_FOUND));
        fittingModel.deleteModel();
        return new CommonResponse(200, "모델 삭제 성공");
    }

    public DataResponse<FittingResultResponseDto> fitting(FittingRequestDto requestDto, Long userId) throws JsonProcessingException {
        // 피팅 가능한 조합인지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        FittingModel fittingModel = fittingModelRepository.findByIdAndUserWhereStatusIsTrue(requestDto.getModelId(), user)
                .orElseThrow(() -> new CustomException(ExceptionType.MODEL_NOT_FOUND));

        FittingCheckDataDto checkDataDto = checkFitting(requestDto, user);
        if (!checkDataDto.getCheck()) {
            return new DataResponse<>(400, "가상피팅 가능한 조합이 아닙니다.");
        }
        // Fast API로 요청해서 피팅된 url 받기
        FittingResultResponseDto responseDto = fastApiService.fitting(checkDataDto.getFittingRequestDtoList());
        responseDto.setOriginImg(fittingModel.getOriginImg());
        responseDto.setModelId(requestDto.getModelId());
        responseDto.setClothesInfoList(checkDataDto.getFittingRequestDtoList());
        return new DataResponse<>(200, "가상피팅 완료", responseDto);
    }

    @Transactional
    public DataResponse<Long> saveFitting(FittingSaveRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        FittingModel fittingModel = fittingModelRepository.findById(requestDto.getModelId())
                .orElseThrow(() -> new CustomException(ExceptionType.MODEL_NOT_FOUND));

        Fitting fitting = Fitting
                .builder()
                .fittingModel(fittingModel)
                .fittingImg(requestDto.getFittingImg())
                .fittingThumnailImg(requestDto.getFittingImg())
                .user(user)
                .build();

        Fitting fittingSave = fittingRepository.save(fitting);

        return new DataResponse<>(200, "가상피팅 저장완료", fittingSave.getId());
    }

    // 가상피팅 날짜 수정
    @Transactional
    public CommonResponse changeWearingAt(FittingDateUpdateRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
        Fitting fitting = fittingRepository.findByIdAndUser(requestDto.getClothesId(), user)
                .orElseThrow(() -> new CustomException(ExceptionType.FITTING_NOT_FOUND));
        String wearingAtMonth = requestDto.getWearingAt();
        fitting.editWearingAt(wearingAtMonth, wearingAtMonth.substring(0, 7));
        return new CommonResponse(200, "날짜 등록/수정 성공");
    }


    public DataResponse<List<FittingListResponseDto>> getFittingList(Long userId) {
        return new DataResponse<>(200, "가상피팅 목록조회");
    }

    public DataResponse<FittingDetailResponseDto> getFittingDetail(Long fittingId, Long userId) {
        return new DataResponse<>(200,"가상피팅 상세조회");
    }


    public FittingCheckDataDto checkFitting(FittingRequestDto requestDto, User user) {
        Clothes upper = clothesRepository.findByIdAndUser(requestDto.getUpperId(), user).orElse(null);
        Clothes lower = clothesRepository.findByIdAndUser(requestDto.getBottomId(), user).orElse(null);
        Clothes dresses = clothesRepository.findByIdAndUser(requestDto.getOnepieceId(), user).orElse(null);

        int checkNun = 0;
        boolean check = true;
        List<FastApiFittingRequestDto> requestDtoList = new ArrayList<>();

        if (upper != null) {
            if (upper.getType().getUpperTypeCode().getUpperTypeName().equals("상의")) {
                checkNun++;
                requestDtoList.add(FastApiFittingRequestDto.of(upper.getId(), "upper", upper.getOriginImg()));
            }
        }

        if (lower != null) {
            if (lower.getType().getUpperTypeCode().getUpperTypeName().equals("하의")) {
                checkNun++;
                requestDtoList.add(FastApiFittingRequestDto.of(lower.getId(), "lower", lower.getOriginImg()));
            }
        }
        if (dresses != null) {
            if (dresses.getType().getUpperTypeCode().getUpperTypeName().equals("한벌옷")) {
                checkNun = checkNun + 2;
                requestDtoList.add(FastApiFittingRequestDto.of(dresses.getId(), "dress", dresses.getOriginImg()));
            }
        }

        if (checkNun == 0 || checkNun > 2)
            check = false;

        return FittingCheckDataDto
                .builder()
                .check(check)
                .fittingRequestDtoList(requestDtoList)
                .build();

    }

}
