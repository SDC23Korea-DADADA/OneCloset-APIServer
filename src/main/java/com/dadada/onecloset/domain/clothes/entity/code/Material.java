package com.dadada.onecloset.domain.clothes.entity.code;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Material {

    @Id
    @Column(name = "material_code")
    private String code;

    private String materialName;

}
