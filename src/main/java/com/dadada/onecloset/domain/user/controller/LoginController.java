package com.dadada.onecloset.domain.user.controller;

import com.dadada.onecloset.domain.user.dto.request.CodeAndUriRequestDto;
import com.dadada.onecloset.domain.user.service.SocialLoginService;
import com.dadada.onecloset.global.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
@CrossOrigin("*")
public class LoginController {

    private final SocialLoginService socialLoginService;

    @PostMapping("/kakao")
    public DataResponse<HashMap<String, Object>> kakaoLogin(@RequestBody CodeAndUriRequestDto requestDto) throws IOException {
        return socialLoginService.kakaoLogin(requestDto);
    }

    @PostMapping("/naver")
    public DataResponse<HashMap<String, Object>> naverLogin(@RequestBody CodeAndUriRequestDto requestDto) throws IOException {
        return socialLoginService.naverLogin(requestDto);
    }

    @PostMapping("/google")
    public DataResponse<HashMap<String, Object>> googleLogin(@RequestBody CodeAndUriRequestDto requestDto) throws IOException {
        return socialLoginService.googleLogin(requestDto);
    }

    @PostMapping("/kakao/access/{token}")
    public DataResponse<HashMap<String, Object>> kakaoLoginByAccessToken(@PathVariable("token") String accessToken) throws IOException {
        return socialLoginService.kakaoLoginByAccessToken(accessToken);
    }

    @PostMapping("/naver/access/{token}")
    public DataResponse<HashMap<String, Object>> naverLoginByAccessToken(@PathVariable("token") String accessToken) throws IOException {
        return socialLoginService.naverLoginByAccessToken(accessToken);
    }

    @PostMapping("/google/access/{token}")
    public DataResponse<HashMap<String, Object>> googleLoginByAccessToken(@PathVariable("token") String accessToken) throws IOException {
        return socialLoginService.googleLoginByAccessToken(accessToken);
    }

}