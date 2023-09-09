package com.dadada.onecloset.domain.clothes.entity.code;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
public class Material {

    @Id
    @Column(name = "material_code")
    private Long code;

    private String materialName;

}
