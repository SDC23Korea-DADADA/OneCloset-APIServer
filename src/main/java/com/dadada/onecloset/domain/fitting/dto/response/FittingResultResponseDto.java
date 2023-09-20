package com.dadada.onecloset.domain.fitting.dto.response;

import com.dadada.onecloset.fastapi.FastApiFittingRequestDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FittingResultResponseDto {

    private String originImg;
    private String fittingImg;
    private Long modelId;
    private List<FastApiFittingRequestDto> clothesInfoList;

}
