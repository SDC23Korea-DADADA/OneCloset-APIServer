package com.dadada.onecloset.domain.codi.dto.response;

import com.dadada.onecloset.domain.codi.entity.Codi;
import com.dadada.onecloset.domain.codi.entity.CodiClothes;
import com.dadada.onecloset.domain.fitting.dto.FittingAndCodiListDetailDto;
import com.dadada.onecloset.domain.fitting.dto.response.FittingListResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodiListResponseDto {

    private Long id;
    private String originImg;
    private String thumbnailImg;
    private String wearingAtMonth;
    private String wearingAtDay;
    private List<FittingAndCodiListDetailDto> clothesList;

    public static CodiListResponseDto of(Codi codi) {

        List<CodiClothes> codiClothesList = codi.getCodiClothesList();
        List<FittingAndCodiListDetailDto> fittingAndCodiListDetailDtoList = new ArrayList<>();

        for (CodiClothes codiClothes: codiClothesList) {
            fittingAndCodiListDetailDtoList.add(FittingAndCodiListDetailDto.of(codiClothes.getClothes()));
        }

        return CodiListResponseDto
                .builder()
                .id(codi.getId())
                .originImg(codi.getOriginImg())
                .thumbnailImg(codi.getThumnailImg())
                .wearingAtMonth(codi.getWearingAtMonth())
                .wearingAtDay(codi.getWearingAtDay())
                .clothesList(fittingAndCodiListDetailDtoList)
                .build();
    }

}
