package com.dadada.onecloset.domain.loundrysolution.entity;

import jakarta.persistence.*;

@Entity
public class CareTip {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "care_tip_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "clothes_care_code")
    private ClothesCare clothesCare;

    private String tip;

}
