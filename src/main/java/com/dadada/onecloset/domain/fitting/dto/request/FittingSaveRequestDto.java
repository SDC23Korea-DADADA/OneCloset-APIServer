package com.dadada.onecloset.domain.fitting.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class FittingSaveRequestDto {

    private Long modelId;
    private String fittingImg;
    private List<Long> clothesIdList;
    private String wearingAt;

}
