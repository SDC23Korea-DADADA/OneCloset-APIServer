package com.dadada.onecloset.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CodeAndUriRequestDto {

    private String code;
    private String redirect;

}
