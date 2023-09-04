package com.dadada.onecloset.domain.clothes.entity.code;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Color {

    @Id
    @Column(name = "color_code")
    private String code;

    private String colorName;

}
