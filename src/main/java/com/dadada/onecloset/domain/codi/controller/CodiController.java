package com.dadada.onecloset.domain.codi.controller;

import com.dadada.onecloset.domain.codi.dto.request.CodiAndFittingListRequestDto;
import com.dadada.onecloset.domain.codi.dto.request.CodiRegistRequestDto;
import com.dadada.onecloset.domain.codi.dto.request.CodiUpdateRequestDto;
import com.dadada.onecloset.domain.codi.dto.response.CodiAndFittingDetailResponseDto;
import com.dadada.onecloset.domain.codi.dto.response.CodiAndFittingListResponseDto;
import com.dadada.onecloset.domain.codi.service.CodiService;
import com.dadada.onecloset.global.CommonResponse;
import com.dadada.onecloset.global.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/codi")
public class CodiController {

    private final CodiService codiService;

    @PostMapping
    public CommonResponse registCodi(Principal principal, @ModelAttribute CodiRegistRequestDto requestDto) {
        Long userId = Long.parseLong(principal.getName());
        return codiService.registCodi(requestDto, userId);
    }

    @PutMapping
    public CommonResponse editCode(Principal principal, @ModelAttribute CodiUpdateRequestDto requestDto) {
        Long userId = Long.parseLong(principal.getName());
        return codiService.editCode(requestDto, userId);
    }

    @DeleteMapping("/{id}")
    public CommonResponse deleteCode(Principal principal, @PathVariable("id") Long codiId) {
        Long userId = Long.parseLong(principal.getName());
        return codiService.deleteCode(codiId, userId);
    }

    @GetMapping("/list")
    public DataResponse<List<CodiAndFittingListResponseDto>> getCodiAndFittingList(Principal principal, @RequestBody CodiAndFittingListRequestDto requestDto) {
        Long userId = Long.parseLong(principal.getName());
        return codiService.getCodiAndFittingList(requestDto, userId);
    }

    @GetMapping("/detail")
    public DataResponse<CodiAndFittingDetailResponseDto> getCodiAndFittingDetail(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return codiService.getCodiAndFittingDetail();
    }


}
