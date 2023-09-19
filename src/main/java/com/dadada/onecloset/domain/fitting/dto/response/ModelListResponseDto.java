package com.dadada.onecloset.domain.fitting.dto.response;

import com.dadada.onecloset.domain.fitting.entity.FittingModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModelListResponseDto {

    private Long modelId;
    private String modelImg;

    public static ModelListResponseDto of(FittingModel fittingModel) {
        return ModelListResponseDto
                .builder()
                .modelId(fittingModel.getId())
                .modelImg(fittingModel.getOriginImg())
                .build();
    }

}
