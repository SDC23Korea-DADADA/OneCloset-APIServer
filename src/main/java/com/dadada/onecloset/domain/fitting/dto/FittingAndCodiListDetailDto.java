package com.dadada.onecloset.domain.fitting.dto;

import com.dadada.onecloset.domain.clothes.entity.Clothes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FittingAndCodiListDetailDto {

    private Long clothesId;
    private String thumbnailImg;

    public static FittingAndCodiListDetailDto of(Clothes clothes) {

        return FittingAndCodiListDetailDto
                .builder()
                .clothesId(clothes.getId())
                .thumbnailImg(clothes.getThumnailImg())
                .build();

    }

}
