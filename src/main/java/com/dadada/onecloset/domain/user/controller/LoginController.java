package com.dadada.onecloset.domain.user.controller;

import com.dadada.onecloset.domain.user.dto.KakaoCodeRequestDto;
import com.dadada.onecloset.domain.user.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

//    @GetMapping("/naver/{code}")
//    public ResponseEntity<?> naverLogin(@PathVariable("code") String code) {
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @GetMapping("/google/{code}")
//    public ResponseEntity<?> googleLogin(@PathVariable("code") String code) {
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

}