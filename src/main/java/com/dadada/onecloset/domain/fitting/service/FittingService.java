package com.dadada.onecloset.domain.fitting.service;

import com.dadada.onecloset.domain.fitting.dto.request.FittingRequestDto;
import com.dadada.onecloset.domain.fitting.dto.request.FittingSaveRequestDto;
import com.dadada.onecloset.domain.fitting.dto.response.FittingDetailResponseDto;
import com.dadada.onecloset.domain.fitting.dto.response.FittingListResponseDto;
import com.dadada.onecloset.domain.fitting.dto.response.FittingResultResponseDto;
import com.dadada.onecloset.domain.fitting.dto.response.ModelListResponseDto;
import com.dadada.onecloset.domain.fitting.entity.FittingModel;
import com.dadada.onecloset.domain.fitting.repository.FittingClothesRepository;
import com.dadada.onecloset.domain.fitting.repository.FittingModelRepository;
import com.dadada.onecloset.domain.user.entity.User;
import com.dadada.onecloset.domain.user.repository.UserRepository;
import com.dadada.onecloset.exception.CustomException;
import com.dadada.onecloset.exception.ExceptionType;
import com.dadada.onecloset.fastapi.FastApiModelRegistResponseDto;
import com.dadada.onecloset.fastapi.FastApiService;
import com.dadada.onecloset.global.CommonResponse;
import com.dadada.onecloset.global.DataResponse;
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

    public DataResponse<FittingResultResponseDto> fitting(FittingRequestDto requestDto, Long userId) {
        // 피팅 가능한 조합인지 확인
        System.out.println(requestDto);

        // 가상피팅 요청해서 피팅된 url 받기

        // Fast API에서 출력시키기

        FittingResultResponseDto responseDto = new FittingResultResponseDto();
        responseDto.setFittingImg("https://fitsta-bucket.s3.ap-northeast-2.amazonaws.com/6ceee621-1dd4-4d4a-aec6-31a7b204d98f-images.jpg");
        responseDto.setFittingImg("https://fitsta-bucket.s3.ap-northeast-2.amazonaws.com/6f4ef69c-0a23-4e9e-8b0e-b0de9e2bdd9f-images.jpg");
        return new DataResponse<>(200, "가상피팅 완료", responseDto);
    }

    public CommonResponse saveFitting(FittingSaveRequestDto requestDto, Long userId) {

        return new CommonResponse(200, "가상피팅 저장완료");

    }

    // 가상피팅 날짜 수정

    public DataResponse<List<FittingListResponseDto>> getFittingList(Long userId) {
        return new DataResponse<>(200, "가상피팅 목록조회");
    }

    public DataResponse<FittingDetailResponseDto> getFittingDetail(Long fittingId, Long userId) {
        return new DataResponse<>(200,"가상피팅 상세조회");
    }

}
