package com.dadada.onecloset.domain.clothes.dto.response;

import com.dadada.onecloset.domain.clothes.entity.Clothes;
import com.dadada.onecloset.domain.clothes.entity.Hashtag;
import com.dadada.onecloset.domain.clothes.entity.Tpo;
import com.dadada.onecloset.domain.clothes.entity.Weather;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClothesListResponseDto {

    private Long clothesId;
    private String thumnailUrl;
    private String type;
    private String upperType;
    private String color;
    private String material;
    private String description;

    private List<String> weatherList;
    private List<String> hashtagList;
    private List<String> tpoList;

    public static ClothesListResponseDto of(Clothes clothes) {

        List<String> weatherList = new ArrayList<>();
        for (Weather weather : clothes.getWeatherList()) {
            weatherList.add(weather.getWeatherName().getWeather());
        }

        List<String> hashtagList = new ArrayList<>();
        for (Hashtag hashtag : clothes.getHashtagList()) {
            hashtagList.add(hashtag.getHashtagName());
        }

        List<String> tpoList = new ArrayList<>();
        for (Tpo tpo : clothes.getTpoList()) {
            tpoList.add(tpo.getTpoName().getTpo());
        }

        return ClothesListResponseDto
                .builder()
                .clothesId(clothes.getId())
                .thumnailUrl(clothes.getThumnailImg())
                .upperType(clothes.getType().getUpperTypeCode().getUpperTypeName())
                .type(clothes.getType().getTypeName())
                .color(clothes.getColor().getColorName())
                .material(clothes.getMaterial().getMaterialName())
                .description(clothes.getDescription())
                .weatherList(weatherList)
                .hashtagList(hashtagList)
                .tpoList(tpoList)
                .build();
    }

}
