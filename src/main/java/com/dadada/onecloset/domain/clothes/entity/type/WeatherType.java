package com.dadada.onecloset.domain.clothes.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum WeatherType {
    SPRING("봄"), SUMMER("여름"),
    FALL("가을"), WINTER("겨울");

    private String weather;

    private static final Map<String, WeatherType> LOOKUP = new HashMap<>();

    // static initializer를 사용하여 모든 enum 값을 map에 저장
    static {
        for (WeatherType type : WeatherType.values()) {
            LOOKUP.put(type.weather, type);
        }
    }

    public static WeatherType fromString(String weather) {
        return LOOKUP.get(weather);
    }

}