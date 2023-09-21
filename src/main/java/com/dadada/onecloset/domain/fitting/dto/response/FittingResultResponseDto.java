package com.dadada.onecloset.domain.fitting.dto.response;

import com.dadada.onecloset.fastapi.FastApiFittingRequestDto;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
public class FittingResultResponseDto {

    private String originImg;
    private String fittingImg;
    private Long modelId;
    private List<FastApiFittingRequestDto> clothesInfoList;

    @Builder
    public FittingResultResponseDto(String originImg, String fittingImg, Long modelId, List<FastApiFittingRequestDto> clothesInfoList) {
        this.fittingImg = fittingImg;
        this.originImg = originImg;
        this.modelId = modelId;
        this.clothesInfoList = clothesInfoList;
    }

}
