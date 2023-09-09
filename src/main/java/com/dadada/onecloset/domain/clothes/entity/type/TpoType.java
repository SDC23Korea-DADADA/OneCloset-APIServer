package com.dadada.onecloset.domain.clothes.entity.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TpoType {

    DAILY("데일리"), BUSSINESS("직장"),
    DATE("데이트"), ANNIVERSARY("경조사"),
    TRAVEL("여행"), HOME("홈웨어"),
    PARTY("파티"), EXERCISE("운동"),
    SCHOOL("학교"), ETC("기타");

    private String tpo;

    private static final Map<String, TpoType> LOOKUP = new HashMap<>();

    static {
        for (TpoType type : TpoType.values()) {
            LOOKUP.put(type.tpo, type);
        }
    }

    public static TpoType fromString(String tpo) {
        TpoType type = LOOKUP.get(tpo);
        if (type == null) {
            throw new IllegalArgumentException("No WeatherType with tpo " + tpo + " found");
        }
        return type;
    }

}
