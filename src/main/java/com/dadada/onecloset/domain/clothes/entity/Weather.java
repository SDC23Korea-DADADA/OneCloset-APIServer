package com.dadada.onecloset.domain.clothes.entity;

import com.dadada.onecloset.domain.clothes.entity.type.WeatherType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Weather {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weather_id")
    private Long id;

    @ManyToOne
    private Clothes clothes;

    private WeatherType weatherName;

    @Builder
    public Weather(Clothes clothes, WeatherType weather) {
        this.clothes = clothes;
        this.weatherName = weather;
    }
}
