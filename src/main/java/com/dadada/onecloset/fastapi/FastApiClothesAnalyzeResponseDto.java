package com.dadada.onecloset.fastapi;

import com.google.gson.JsonElement;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FastApiClothesAnalyzeResponseDto {

    private String url;
    private String type;
    private String color;
    private String material;

    public static FastApiClothesAnalyzeResponseDto of(JsonElement jsonElement) {
        return FastApiClothesAnalyzeResponseDto
                .builder()
                .url(jsonElement.getAsJsonObject().get("image").getAsString())
                .type(jsonElement.getAsJsonObject().get("type").getAsString())
                .color(jsonElement.getAsJsonObject().get("color").getAsString())
                .material(jsonElement.getAsJsonObject().get("material").getAsString())
                .build();
    }

}
