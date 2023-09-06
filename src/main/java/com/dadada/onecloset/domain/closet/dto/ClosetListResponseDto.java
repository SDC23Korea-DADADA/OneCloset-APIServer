package com.dadada.onecloset.domain.closet.dto;

import com.dadada.onecloset.domain.closet.entity.Closet;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClosetListResponseDto {

    private Long closetId;
    private String name;
    private Integer icon;
    private String colorCode;

    @Builder
    public ClosetListResponseDto(Closet closet){
        this.closetId = closet.getId();
        this.name = closet.getName();
        this.icon = closet.getIcon();
        this.colorCode = closet.getIconColor();
    }

}
