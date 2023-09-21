package com.dadada.onecloset.domain.fitting.dto.response;

import com.dadada.onecloset.domain.clothes.entity.Clothes;
import com.dadada.onecloset.domain.fitting.entity.Fitting;
import com.dadada.onecloset.domain.fitting.entity.FittingClothes;
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
public class FittingListResponseDto {

    private Long id;
    private String originImg;
    private String fittingImg;
    private String fittingThumbnailImg;
    private String wearingAtMonth;
    private String wearingAtDay;
    private List<FittingListDetailResponseDto> clothesList;

    public static FittingListResponseDto of(Fitting fitting) {

        List<FittingClothes> fittingClothesList = fitting.getFittingClothesList();
        List<FittingListDetailResponseDto> fittingListDetailResponseDtoList = new ArrayList<>();

        for (FittingClothes fittingClothes : fittingClothesList) {
            fittingListDetailResponseDtoList.add(FittingListDetailResponseDto.of(fittingClothes.getClothes()));
        }

        return FittingListResponseDto
                .builder()
                .id(fitting.getId())
                .originImg(fitting.getFittingModel().getOriginImg())
                .fittingImg(fitting.getFittingImg())
                .fittingThumbnailImg(fitting.getFittingThumnailImg())
                .wearingAtMonth(fitting.getWearingAtMonth())
                .wearingAtDay(fitting.getWearingAtDay())
                .clothesList(fittingListDetailResponseDtoList)
                .build();

    }

}
