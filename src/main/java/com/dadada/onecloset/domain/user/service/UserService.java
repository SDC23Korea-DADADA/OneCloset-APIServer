package com.dadada.onecloset.domain.user.service;


import com.dadada.onecloset.domain.user.dto.response.UserInfoResponseDto;
import com.dadada.onecloset.domain.user.entity.User;
import com.dadada.onecloset.domain.user.entity.type.LoginType;
import com.dadada.onecloset.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserInfoResponseDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        return new UserInfoResponseDto(user);
    }

    @Transactional
    public void leaveService(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        user.leaveService();
    }

    @Transactional
    public void tempRejoinService(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        user.tempRejoin();
    }

    @Transactional
    public User enterUser(HashMap<String, Object> userInfo, LoginType loginType) {
        User user = User
                .builder()
                .loginId(userInfo.get("loginId").toString())
                .loginType(loginType)
                .nickname(userInfo.get("nickname").toString())
                .profileImg(userInfo.get("profileImg").toString())
                .email(userInfo.get("email").toString())
                .build();
        return userRepository.save(user);
    }

    boolean isEmpty(String loginId, LoginType loginType) {
        Optional<User> checkUser = userRepository.findByLoginIdAndLoginType(loginId, loginType);
        return checkUser.isEmpty();
    }

}
