package com.dadada.onecloset.domain.clothes.dto.response;

import com.dadada.onecloset.domain.clothes.entity.Clothes;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminClothesResponseDto {

    private Long clothesId;
    private String image;
    private String type;
    private String material;
    private String color;
    private String colerCode;

    public static AdminClothesResponseDto of(Clothes clothes) {

        return AdminClothesResponseDto
                .builder()
                .clothesId(clothes.getId())
                .image(clothes.getOriginImg())
                .type(clothes.getType().getTypeName())
                .material(clothes.getMaterial().getMaterialName())
                .color(clothes.getColor().getColorName())
                .colerCode(clothes.getColor().getCode())
                .build();
    }

}
