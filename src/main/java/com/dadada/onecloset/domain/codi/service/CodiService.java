package com.dadada.onecloset.domain.codi.service;

import com.dadada.onecloset.domain.codi.dto.request.CodiAndFittingListRequestDto;
import com.dadada.onecloset.domain.codi.dto.request.CodiRegistRequestDto;
import com.dadada.onecloset.domain.codi.dto.request.CodiUpdateRequestDto;
import com.dadada.onecloset.domain.codi.dto.response.CodiAndFittingDetailResponseDto;
import com.dadada.onecloset.domain.codi.dto.response.CodiAndFittingListResponseDto;
import com.dadada.onecloset.domain.codi.repository.CodiRepository;
import com.dadada.onecloset.global.CommonResponse;
import com.dadada.onecloset.global.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CodiService {

    private final CodiRepository codiRepository;

    public CommonResponse registCodi(CodiRegistRequestDto requestDto, Long userId) {
        return new CommonResponse(200,"코디 등록 성공");
    }

    public CommonResponse editCode(CodiUpdateRequestDto requestDto, Long userId) {
        return new CommonResponse(200, "코디 수정 성공");
    }

    public CommonResponse deleteCode(Long codiId, Long userId) {
        return new CommonResponse(200, "코디 삭제 성공");
    }

    public DataResponse<List<CodiAndFittingListResponseDto>> getCodiAndFittingList(CodiAndFittingListRequestDto requestDto, Long userId) {
        List<CodiAndFittingListResponseDto> responseDtoList = new ArrayList<>();
        return new DataResponse<>(200, "코디, 가상피팅 목록 조회", responseDtoList);
    }

    public DataResponse<CodiAndFittingDetailResponseDto> getCodiAndFittingDetail() {
        CodiAndFittingDetailResponseDto responseDto = new CodiAndFittingDetailResponseDto();
        return new DataResponse<>(200, "상세 보기", responseDto);
    }

}
