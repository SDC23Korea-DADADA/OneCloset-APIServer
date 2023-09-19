package com.dadada.onecloset.temp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ColorCheckResponseDto {

    private String colorName;
    private String colorCode;

    @Builder
    public ColorCheckResponseDto(String colorName, String colorCode) {
        this.colorName = colorName;
        this.colorCode = colorCode;
    }

}
