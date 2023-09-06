package com.dadada.onecloset.domain.closet.controller;

import com.dadada.onecloset.domain.closet.dto.ClosetCreateRequestDto;
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
@RequestMapping("/api/closet")
public class ClosetController {

    private final ClosetService closetService;
    private final JwtUtil jwtUtil;

    @PostMapping
    public CommonResponse createCloset(HttpServletRequest request, @RequestBody ClosetCreateRequestDto requestDto) {
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        closetService.createCloset(requestDto, userId);
        return new CommonResponse(200, "옷장 생성 성공");
    }

    @GetMapping("/list")
    public DataResponse<List<ClosetListResponseDto>> getClosetList(HttpServletRequest request){
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        List<ClosetListResponseDto> responseDtoList = closetService.getClosetList(userId);
        return new DataResponse<>(200, "옷장 목록 조회 성공", responseDtoList);
    }

}
