package com.dadada.onecloset.domain.clothes.dto.response;

import com.dadada.onecloset.domain.clothes.entity.code.Color;
import com.dadada.onecloset.domain.laundrysolution.entity.CareTip;
import com.dadada.onecloset.domain.laundrysolution.entity.ClothesCare;
import com.dadada.onecloset.domain.laundrysolution.entity.LaundryTip;
import com.dadada.onecloset.fastapi.FastApiClothesAnalyzeResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClothesAnalyzeResponseDto {

    private String image;

    private String color;
    private String colorCode;

    private String type;
    private String material;

    public static ClothesAnalyzeResponseDto of(FastApiClothesAnalyzeResponseDto responseDto, Color color) throws IOException {

        return ClothesAnalyzeResponseDto
                .builder()
                .image(responseDto.getUrl())
                .color(color.getColorName())
                .colorCode(color.getCode())
                .type(responseDto.getType())
                .material(responseDto.getMaterial())
                .build();
    }

}
