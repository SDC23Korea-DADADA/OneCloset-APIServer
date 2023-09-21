package com.dadada.onecloset.domain.fitting.dto.response;

import com.dadada.onecloset.domain.clothes.entity.Clothes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FittingListDetailResponseDto {

    private Long clothesId;
    private String thumbnailImg;

    public static FittingListDetailResponseDto of(Clothes clothes) {

        return FittingListDetailResponseDto
                .builder()
                .clothesId(clothes.getId())
                .thumbnailImg(clothes.getThumnailImg())
                .build();

    }

}
