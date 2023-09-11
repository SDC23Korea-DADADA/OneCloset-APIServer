package com.dadada.onecloset.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoCodeRequestDto {

    private String code;
    private String redirect;

}
