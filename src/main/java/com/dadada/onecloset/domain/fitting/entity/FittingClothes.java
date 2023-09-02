package com.dadada.onecloset.domain.fitting.entity;

import com.dadada.onecloset.domain.clothes.entity.Clothes;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FittingClothes {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fitting_clothes_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fitting_id")
    private Fitting fitting;

    @ManyToOne
    @JoinColumn(name = "clothes_id")
    private Clothes clothes;

    @Builder
    public FittingClothes(Fitting fitting, Clothes clothes) {
        this.fitting = fitting;
        this.clothes = clothes;
    }
}
