package com.dadada.onecloset.domain.user.dto.response;

import com.dadada.onecloset.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {

    Long userId;
    String email;
    String gender;
    String social;
    String nickname;
    String profileImg;

    public UserInfoResponseDto(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.gender = user.getGenderType().toString();
        this.social = user.getLoginType().toString();
        this.nickname = user.getNickname();
        this.profileImg = user.getProfileImg();
    }
}
