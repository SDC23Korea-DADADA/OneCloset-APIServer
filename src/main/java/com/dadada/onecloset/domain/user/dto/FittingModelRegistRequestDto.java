package com.dadada.onecloset.domain.user.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FittingModelRegistRequestDto {

    private String segmantation;
    private String poseSkeleton;
    private String keypoints;
    private String denseModel;

}
