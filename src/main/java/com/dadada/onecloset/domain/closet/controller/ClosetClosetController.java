package com.dadada.onecloset.domain.closet.controller;

import com.dadada.onecloset.domain.closet.dto.ClosetListResponseDto;
import com.dadada.onecloset.domain.closet.service.ClosetService;
import com.dadada.onecloset.global.CommonResponse;
import com.dadada.onecloset.global.DataResponse;
import com.dadada.onecloset.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/closet/clothes")
public class ClosetClosetController {

    private final ClosetService closetService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public CommonResponse registClothesToCloset(HttpServletRequest request){
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        return closetService.getClosetList(userId);
    }

    @DeleteMapping
    public DataResponse<List<ClosetListResponseDto>> deleteClothesToCloset(HttpServletRequest request){
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        return closetService.getClosetList(userId);
    }

}
