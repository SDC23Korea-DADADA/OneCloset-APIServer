package com.dadada.onecloset.domain.user.controller;

import com.dadada.onecloset.domain.user.dto.response.UserInfoResponseDto;
import com.dadada.onecloset.domain.user.service.UserService;
import com.dadada.onecloset.global.CommonResponse;
import com.dadada.onecloset.global.DataResponse;
import com.dadada.onecloset.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping
    public DataResponse<UserInfoResponseDto> getUserInfo(HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        UserInfoResponseDto responseDto = userService.getUserInfo(userId);
        return new DataResponse<>(200, "유저 정보 조회 성공", responseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/leave")
    public CommonResponse leaveService(HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        userService.leaveService(userId);
        return new CommonResponse(200, "탈퇴 성공");
    }

    @PostMapping("/temp/rejoin/{userId}")
    public CommonResponse rejoinService(@PathVariable Long userId) {
        userService.tempRejoinService(userId);
        return new CommonResponse(200, "재가입 성공");
    }

}
