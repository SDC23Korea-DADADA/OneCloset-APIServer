package com.dadada.onecloset.domain.fitting.service;

import com.dadada.onecloset.domain.fitting.dto.request.FittingRequestDto;
import com.dadada.onecloset.domain.fitting.dto.request.FittingSaveRequestDto;
import com.dadada.onecloset.domain.fitting.dto.response.FittingDetailResponseDto;
import com.dadada.onecloset.domain.fitting.dto.response.FittingListResponseDto;
import com.dadada.onecloset.domain.fitting.dto.response.FittingResultResponseDto;
import com.dadada.onecloset.global.CommonResponse;
import com.dadada.onecloset.global.DataResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FittingService {

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
