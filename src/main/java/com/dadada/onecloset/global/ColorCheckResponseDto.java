package com.dadada.onecloset.global;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ColorCheckResponseDto {

    private String colorName;
    private String colorCode;
    private Long colorLong;

    @Builder
    public ColorCheckResponseDto(String colorName, String colorCode, Long colorLong) {
        this.colorName = colorName;
        this.colorCode = colorCode;
        this.colorLong = colorLong;
    }

}
