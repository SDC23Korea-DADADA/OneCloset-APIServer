package com.dadada.onecloset.domain.clothes.dto.response;

import com.dadada.onecloset.domain.clothes.entity.Clothes;
import com.dadada.onecloset.domain.clothes.entity.Hashtag;
import com.dadada.onecloset.domain.clothes.entity.Tpo;
import com.dadada.onecloset.domain.clothes.entity.Weather;
import com.dadada.onecloset.domain.laundrysolution.entity.CareTip;
import com.dadada.onecloset.domain.laundrysolution.entity.ClothesCare;
import com.dadada.onecloset.domain.laundrysolution.entity.LaundryTip;
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
public class ClothesDetailResponseDto {

    private Long clothesId;

    private String img;

    private String color;
    private String colorCode;

    private String type;
    private String material;
    private String description;

    private String laundry;
    private String dryer;
    private String airDressor;

    private List<String> weatherList;
    private List<String> tpoList;
    private List<String> hashtagList;

    private List<String> laundryTipList;
    private List<String> careTipList;

    public static ClothesDetailResponseDto of(Clothes clothes, ClothesCare clothesCare) {

        List<String> weatherList = new ArrayList<>();
        for (Weather weather: clothes.getWeatherList()) {
            weatherList.add(weather.getWeatherName().getWeather());
        }

        List<String> tpoList = new ArrayList<>();
        for (Tpo tpo: clothes.getTpoList()) {
            tpoList.add(tpo.getTpoName().getTpo());
        }

        List<String> hashtagList = new ArrayList<>();
        for (Hashtag hashtag : clothes.getHashtagList()) {
            hashtagList.add(hashtag.getHashtagName());
        }

        List<String> laundryTipList = new ArrayList<>();
        for (LaundryTip laundryTip: clothesCare.getLaundryTipList()) {
            laundryTipList.add(laundryTip.getTip());
        }

        List<String> careTipList = new ArrayList<>();
        for (CareTip careTip : clothesCare.getCareTipList()) {
            careTipList.add(careTip.getTip());
        }

        return ClothesDetailResponseDto
                .builder()
                .clothesId(clothes.getId())
                .img(clothes.getOriginImg())
                .color(clothes.getColor().getColorName())
                .colorCode(clothes.getColor().getCode())
                .type(clothes.getType().getTypeName())
                .material(clothes.getMaterial().getMaterialName())
                .description(clothes.getDescription())
                .laundry(clothesCare.getLaundryCourse())
                .dryer(clothesCare.getDryerCourse())
                .airDressor(clothesCare.getAirDresserCourse())
                .weatherList(weatherList)
                .tpoList(tpoList)
                .hashtagList(hashtagList)
                .laundryTipList(laundryTipList)
                .careTipList(careTipList)
                .build();
    }

}
