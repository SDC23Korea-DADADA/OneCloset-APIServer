package com.dadada.onecloset.domain.laundrysolution.entity;

import jakarta.persistence.*;

@Entity
public class LaundryTip {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "laundry_tip_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothes_care_code")
    private ClothesCare clothesCare;

    private String tip;

}
