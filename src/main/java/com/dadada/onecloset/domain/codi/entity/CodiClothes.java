package com.dadada.onecloset.domain.codi.entity;

import com.dadada.onecloset.domain.clothes.entity.Clothes;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodiClothes {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codi_clothes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codi_id")
    private Codi codi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothes_id")
    private Clothes clothes;

    @Builder
    public CodiClothes(Codi codi, Clothes clothes) {
        this.codi = codi;
        this.clothes = clothes;
    }
}
