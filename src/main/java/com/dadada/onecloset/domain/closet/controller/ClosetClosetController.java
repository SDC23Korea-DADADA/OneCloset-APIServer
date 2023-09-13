package com.dadada.onecloset.domain.closet.controller;

import com.dadada.onecloset.domain.closet.dto.request.ClosetClothesRequestDto;
import com.dadada.onecloset.domain.closet.service.ClosetClothesService;
import com.dadada.onecloset.global.CommonResponse;
import com.dadada.onecloset.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/closet/clothes")
public class ClosetClosetController {

    private final ClosetClothesService closetClothesService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public CommonResponse registClothesToCloset(Principal principal, @RequestBody ClosetClothesRequestDto requestDto){
        Long userId = Long.parseLong(principal.getName());
        return closetClothesService.registClothesToCloset(requestDto, userId);
    }

    @DeleteMapping
    public CommonResponse deleteClothesFromCloset(Principal principal, @RequestBody ClosetClothesRequestDto requestDto){
        Long userId = Long.parseLong(principal.getName());
        return closetClothesService.deleteClothesToCloset(requestDto, userId);
    }

}
