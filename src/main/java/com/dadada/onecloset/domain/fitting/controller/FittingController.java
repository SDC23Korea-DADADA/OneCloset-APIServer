package com.dadada.onecloset.domain.fitting.controller;

import com.dadada.onecloset.domain.fitting.dto.request.FittingRequestDto;
import com.dadada.onecloset.domain.fitting.dto.request.FittingSaveRequestDto;
import com.dadada.onecloset.domain.fitting.dto.request.FittingDateUpdateRequestDto;
import com.dadada.onecloset.domain.fitting.dto.response.FittingDetailResponseDto;
import com.dadada.onecloset.domain.fitting.dto.response.FittingListResponseDto;
import com.dadada.onecloset.domain.fitting.dto.response.FittingResultResponseDto;
import com.dadada.onecloset.domain.fitting.dto.response.ModelListResponseDto;
import com.dadada.onecloset.domain.fitting.service.FittingService;
import com.dadada.onecloset.global.CommonResponse;
import com.dadada.onecloset.global.DataResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fitting")
public class FittingController {

    private final FittingService fittingService;

    @PostMapping("/model")
    public CommonResponse registFittingModel(Principal principal, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        Long userId = Long.parseLong(principal.getName());
        return fittingService.registFittingModel(multipartFile, userId);
    }

    @GetMapping("/model/list")
    public DataResponse<List<ModelListResponseDto>> getFittingModelList(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return fittingService.getFittingModelList(userId);
    }

    @DeleteMapping("/model/{id}")
    public CommonResponse deleteModel(Principal principal, @PathVariable("id") Long modelId) {
        Long userId = Long.parseLong(principal.getName());
        return fittingService.deleteModel(modelId, userId);
    }

    @PostMapping
    public DataResponse<FittingResultResponseDto> fitting(Principal principal, @RequestBody FittingRequestDto requestDto) throws JsonProcessingException {
        Long userId = Long.parseLong(principal.getName());
        return fittingService.fitting(requestDto, userId);
    }

    @DeleteMapping("/{id}")
    public CommonResponse deleteFitting(Principal principal, @PathVariable("id") Long fittingId) {
        Long userId = Long.parseLong(principal.getName());
        return fittingService.deleteFitting(fittingId, userId);
    }

    @PostMapping("/save")
    public DataResponse<Long> saveFitting(Principal principal, @RequestBody FittingSaveRequestDto requestDto) {
        Long userId = Long.parseLong(principal.getName());
        return fittingService.saveFitting(requestDto, userId);
    }

    @PostMapping("/time")
    public CommonResponse changeWearingAt(Principal principal, @RequestBody FittingDateUpdateRequestDto requestDto) {
        Long userId = Long.parseLong(principal.getName());
        return fittingService.changeWearingAt(requestDto, userId);
    }

    @GetMapping("/list")
    public DataResponse<List<FittingListResponseDto>> getFittingList(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return fittingService.getFittingList(userId);
    }

    @GetMapping("/list/month/{date}")
    public DataResponse<List<FittingListResponseDto>> getFittingListByMonth(Principal principal, @PathVariable("date") String date) {
        Long userId = Long.parseLong(principal.getName());
        return fittingService.getFittingListByMonth(date, userId);
    }


}
