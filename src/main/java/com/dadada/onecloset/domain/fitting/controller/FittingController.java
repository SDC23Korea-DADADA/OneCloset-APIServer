package com.dadada.onecloset.domain.fitting.controller;

import com.dadada.onecloset.domain.fitting.dto.request.FittingRequestDto;
import com.dadada.onecloset.domain.fitting.dto.request.FittingSaveRequestDto;
import com.dadada.onecloset.domain.fitting.dto.response.FittingDetailResponseDto;
import com.dadada.onecloset.domain.fitting.dto.response.FittingListResponseDto;
import com.dadada.onecloset.domain.fitting.dto.response.FittingResultResponseDto;
import com.dadada.onecloset.domain.fitting.service.FittingService;
import com.dadada.onecloset.global.CommonResponse;
import com.dadada.onecloset.global.DataResponse;
import com.dadada.onecloset.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fitting")
public class FittingController {

    private final JwtUtil jwtUtil;
    private final FittingService fittingService;

    @PostMapping
    public DataResponse<FittingResultResponseDto> fitting(HttpServletRequest request, @ModelAttribute FittingRequestDto requestDto) {
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        return fittingService.fitting(requestDto, userId);
    }

    @PostMapping("/save")
    public CommonResponse savelFitting(HttpServletRequest request, @ModelAttribute FittingSaveRequestDto requestDto) {
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        return fittingService.saveFitting(requestDto, userId);
    }

    @GetMapping("/list")
    public DataResponse<List<FittingListResponseDto>> getFittingList(HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        return fittingService.getFittingList(userId);
    }

    @GetMapping("/{id}")
    public DataResponse<FittingDetailResponseDto> getFittingDetail(HttpServletRequest request, @PathVariable("id") Long fittingId) {
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        return fittingService.getFittingDetail(fittingId, userId);
    }

}
