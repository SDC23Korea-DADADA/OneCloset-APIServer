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
    private String segmentation;
    private String skeleton;
    private String keypoints;
    private String denseModel;

    public static FastApiModelRegistResponseDto of(JsonElement jsonElement) {
        return FastApiModelRegistResponseDto
                .builder()
                .originImg(jsonElement.getAsJsonObject().get("originImg").getAsString())
                .segmentation(jsonElement.getAsJsonObject().get("segmentation").getAsString())
                .skeleton(jsonElement.getAsJsonObject().get("skeleton").getAsString())
                .keypoints(jsonElement.getAsJsonObject().get("keypoints").getAsString())
                .denseModel(jsonElement.getAsJsonObject().get("denseModel").getAsString())
                .build();
    }

}
