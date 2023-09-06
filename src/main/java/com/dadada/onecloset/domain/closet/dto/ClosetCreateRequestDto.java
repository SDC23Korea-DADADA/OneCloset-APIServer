package com.dadada.onecloset.domain.closet.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClosetCreateRequestDto {

    private String name;
    private Integer icon;
    private String colorCode;

}
