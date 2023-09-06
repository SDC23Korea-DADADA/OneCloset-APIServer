package com.dadada.onecloset.domain.user.controller;

import com.dadada.onecloset.domain.user.dto.UserInfoResponseDto;
import com.dadada.onecloset.domain.user.service.UserService;
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
    public ResponseEntity<UserInfoResponseDto> getUserInfo(HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        UserInfoResponseDto responseDto = userService.getUserInfo(userId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/leave")
    public ResponseEntity<?> leaveService(HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromHttpHeader(request);
        userService.leaveService(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
