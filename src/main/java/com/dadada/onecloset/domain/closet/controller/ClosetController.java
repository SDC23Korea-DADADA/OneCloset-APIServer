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

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/closet")
public class ClosetController {

    private final ClosetService closetService;

    @PostMapping
    public CommonResponse createCloset(Principal principal, @RequestBody ClosetCreateRequestDto requestDto) {
        Long userId = Long.parseLong(principal.getName());
        System.out.println(requestDto);
        return closetService.createCloset(requestDto, userId);
    }

    @GetMapping("/list")
    public DataResponse<List<ClosetListResponseDto>> getClosetList(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return closetService.getClosetList(userId);
    }

    @PutMapping
    public CommonResponse editClosetInfo(Principal principal, @RequestBody ClosetEditRequestDto requestDto) {
        Long userId = Long.parseLong(principal.getName());
        return closetService.editClosetInfo(requestDto, userId);
    }

    @DeleteMapping("/{id}")
    public CommonResponse deleteCloset(Principal principal, @PathVariable("id") Long closetId) {
        Long userId = Long.parseLong(principal.getName());
        return closetService.deleteCloset(closetId, userId);
    }

}
