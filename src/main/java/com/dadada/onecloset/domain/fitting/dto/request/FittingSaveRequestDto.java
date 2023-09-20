package com.dadada.onecloset.domain.fitting.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class FittingSaveRequestDto {

    private Long modelId;
    private String fittingImg;
    private List<Long> clothesList;

}
