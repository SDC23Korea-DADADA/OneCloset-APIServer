package com.dadada.onecloset.fastapi;

import com.google.gson.JsonElement;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FastApiModelRegistResponseDto {

    private String originImg;
    private String labelMap;
    private String skeleton;
    private String keypoint;
    private String dense;
    private String denseNpz;

    public static FastApiModelRegistResponseDto of(JsonElement jsonElement) {
        return FastApiModelRegistResponseDto
                .builder()
                .originImg(jsonElement.getAsJsonObject().get("originImg").getAsString())
                .labelMap(jsonElement.getAsJsonObject().get("labelMap").getAsString())
                .skeleton(jsonElement.getAsJsonObject().get("skeleton").getAsString())
                .keypoint(jsonElement.getAsJsonObject().get("keypoint").getAsString())
                .dense(jsonElement.getAsJsonObject().get("dense").getAsString())
                .denseNpz(jsonElement.getAsJsonObject().get("denseNpz").getAsString())
                .build();
    }

}
