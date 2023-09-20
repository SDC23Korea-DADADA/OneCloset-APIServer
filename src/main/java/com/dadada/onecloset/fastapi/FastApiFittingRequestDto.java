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

    private String type;
    private String url;

    public static FastApiFittingRequestDto of(String type, String url) {
        return FastApiFittingRequestDto
                .builder()
                .type(type)
                .url(url)
                .build();
    }

}
