package com.dadada.onecloset.domain.fitting.service;

import com.dadada.onecloset.domain.fitting.dto.request.FittingRequestDto;
import com.dadada.onecloset.domain.fitting.dto.request.FittingSaveRequestDto;
import com.dadada.onecloset.domain.fitting.dto.response.FittingDetailResponseDto;
import com.dadada.onecloset.domain.fitting.dto.response.FittingListResponseDto;
import com.dadada.onecloset.domain.fitting.dto.response.FittingResultResponseDto;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FittingService {

    private final FastApiService fastApiService;
    private final UserRepository userRepository;
    private final FittingModelRepository fittingModelRepository;
    private final FittingClothesRepository fittingClothesRepository;

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

    public DataResponse<FittingResultResponseDto> fitting(FittingRequestDto requestDto, Long userId) {
        return new DataResponse<>(200, "가상피팅 완료");
    }

    public CommonResponse saveFitting(FittingSaveRequestDto requestDto, Long userId) {
        return new CommonResponse(200, "가상피팅 저장완료");
    }

    public DataResponse<List<FittingListResponseDto>> getFittingList(Long userId) {
        return new DataResponse<>(200, "가상피팅 목록조회");
    }

    public DataResponse<FittingDetailResponseDto> getFittingDetail(Long fittingId, Long userId) {
        return new DataResponse<>(200,"가상피팅 상세조회");
    }

}
