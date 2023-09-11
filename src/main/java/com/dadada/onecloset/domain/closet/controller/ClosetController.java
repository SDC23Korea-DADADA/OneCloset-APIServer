package com.dadada.onecloset.domain.closet.controller;

import com.dadada.onecloset.domain.closet.dto.request.ClosetCreateRequestDto;
import com.dadada.onecloset.domain.closet.dto.request.ClosetEditRequestDto;
import com.dadada.onecloset.domain.closet.dto.response.ClosetListResponseDto;
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
        System.out.println(requestDto);
        return closetService.createCloset(requestDto, userId);
    }

    @GetMapping("/list")
    public DataResponse<List<ClosetListResponseDto>> getClosetList(HttpServletRequest request){
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        return closetService.getClosetList(userId);
    }

    @PutMapping
    public CommonResponse editClosetInfo(HttpServletRequest request, @RequestBody ClosetEditRequestDto requestDto) {
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        return closetService.editClosetInfo(requestDto, userId);
    }

    @DeleteMapping("/{id}")
    public CommonResponse deleteCloset(HttpServletRequest request, @PathVariable("id") Long closetId) {
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        return closetService.deleteCloset(closetId, userId);
    }

}
