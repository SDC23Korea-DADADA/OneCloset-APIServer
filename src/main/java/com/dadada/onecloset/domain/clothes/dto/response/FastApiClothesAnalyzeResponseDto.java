package com.dadada.onecloset.domain.clothes.dto.response;

import com.google.gson.JsonElement;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FastApiClothesAnalyzeResponseDto {

    private String url;
    private String type;
    private String color;
    private String material;

    public static FastApiClothesAnalyzeResponseDto of(JsonElement jsonElement) {
        return FastApiClothesAnalyzeResponseDto
                .builder()
                .url(jsonElement.getAsJsonObject().get("url").getAsString())
                .type(jsonElement.getAsJsonObject().get("type").getAsString())
                .color(jsonElement.getAsJsonObject().get("color").getAsString())
                .material(jsonElement.getAsJsonObject().get("material").getAsString())
                .build();
    }

}
