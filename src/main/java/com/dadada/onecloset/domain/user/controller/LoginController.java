package com.dadada.onecloset.domain.user.controller;

import com.dadada.onecloset.domain.user.dto.request.KakaoCodeRequestDto;
import com.dadada.onecloset.domain.user.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
@CrossOrigin("*")
public class LoginController {

    private final KakaoLoginService kakaoLoginService;

    @PostMapping("/kakao")
    public ResponseEntity<?> kakaoCallback(@RequestBody KakaoCodeRequestDto requestDto) {
        return kakaoLoginService.kakaoLogin(requestDto);
    }

    @PostMapping("/kakao/access/{token}")
    public ResponseEntity<?> kakaoLogin(@PathVariable("token") String accessToken) {
        return kakaoLoginService.kakaoLoginByAccessToken(accessToken);
    }

}