package com.dadada.onecloset.domain.fitting.entity.code;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Mannequin {

    @Id
    @Column(name = "mannequin_code")
    private String code;

    private String path;

}
