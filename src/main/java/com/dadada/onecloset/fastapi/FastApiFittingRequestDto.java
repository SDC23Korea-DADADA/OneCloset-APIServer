package com.dadada.onecloset.fastapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FastApiFittingRequestDto {

    private Long clothesId;
    private String type;
    private String url;

    public static FastApiFittingRequestDto of(Long clothesId, String type, String url) {
        return FastApiFittingRequestDto
                .builder()
                .clothesId(clothesId)
                .type(type)
                .url(url)
                .build();
    }

}
