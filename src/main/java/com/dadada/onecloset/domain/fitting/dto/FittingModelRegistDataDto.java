package com.dadada.onecloset.domain.fitting.dto;

import com.dadada.onecloset.domain.fitting.entity.FittingModel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FittingModelRegistDataDto {

    FittingModel fittingModel;
    String url;

    @Builder
    public FittingModelRegistDataDto(FittingModel fittingModel, String url) {
        this.fittingModel = fittingModel;
        this.url = url;
    }

}
