package com.dadada.onecloset.domain.fitting.dto.response;

import com.dadada.onecloset.domain.codi.dto.response.CodiListResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FittingAndCodiResponseDto {

    private List<FittingListResponseDto> fittingList;
    private List<CodiListResponseDto> codiList;

    @Builder
    public FittingAndCodiResponseDto(List<FittingListResponseDto> fittingList, List<CodiListResponseDto> codiList) {
        this.fittingList = fittingList;
        this.codiList = codiList;
    }


}
