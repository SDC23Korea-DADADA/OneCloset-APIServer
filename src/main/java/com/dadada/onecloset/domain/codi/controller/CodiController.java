package com.dadada.onecloset.domain.codi.controller;

import com.dadada.onecloset.domain.codi.dto.request.CodiRegistRequestDto;
import com.dadada.onecloset.domain.codi.dto.request.CodiUpdateRequestDto;
import com.dadada.onecloset.domain.codi.service.CodiService;
import com.dadada.onecloset.global.CommonResponse;
import com.dadada.onecloset.global.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/codi")
public class CodiController {

    private final CodiService codiService;

    @PostMapping
    public DataResponse<Long> registCodi(Principal principal, @ModelAttribute CodiRegistRequestDto requestDto) throws IOException {
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

}
