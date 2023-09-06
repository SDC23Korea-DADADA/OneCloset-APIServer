package com.dadada.onecloset.domain.closet.controller;

import com.dadada.onecloset.domain.closet.dto.ClosetCreateRequestDto;
import com.dadada.onecloset.domain.closet.service.ClosetService;
import com.dadada.onecloset.global.CommonResponse;
import com.dadada.onecloset.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
