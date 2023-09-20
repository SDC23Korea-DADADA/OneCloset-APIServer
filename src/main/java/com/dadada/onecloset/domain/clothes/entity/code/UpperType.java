package com.dadada.onecloset.domain.clothes.entity.code;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class UpperType {

    @Id
    @Column(name = "upper_type_code")
    private Long code;

    private String upperTypeName;

}
