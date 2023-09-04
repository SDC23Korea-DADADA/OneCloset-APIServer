package com.dadada.onecloset.domain.closet.entity;

import com.dadada.onecloset.domain.clothes.entity.Clothes;
import com.dadada.onecloset.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClosetClothes extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "closet_clothes_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "closet_id")
    private Closet closet;

    @ManyToOne
    @JoinColumn(name = "clothes_id")
    private Clothes clothes;

    @Builder
    public ClosetClothes(Closet closet, Clothes clothes) {
        this.closet = closet;
        this.clothes = clothes;
    }

}
