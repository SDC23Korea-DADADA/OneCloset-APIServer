package com.dadada.onecloset.domain.closet.dto.request;

import lombok.Getter;

@Getter
public class ClosetEditRequestDto {

    private Long closetId;
    private String name;
    private Integer icon;
    private String colorCode;

}
