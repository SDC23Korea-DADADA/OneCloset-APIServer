package com.dadada.onecloset.domain.laundrysolution.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CareTip {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "care_tip_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothes_care_code")
    private ClothesCare clothesCare;

    private String tip;

}
