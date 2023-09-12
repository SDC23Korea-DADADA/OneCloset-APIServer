package com.dadada.onecloset.domain.closet.dto.response;

import com.dadada.onecloset.domain.closet.entity.Closet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClosetListResponseDto {

    private Long closetId;
    private String name;
    private Integer icon;
    private String colorCode;

    public static ClosetListResponseDto of(Closet closet) {
        return ClosetListResponseDto.builder()
                .closetId(closet.getId())
                .name(closet.getName())
                .icon(closet.getIcon())
                .colorCode(closet.getIconColor())
                .build();
    }

}
