package com.dadada.onecloset.domain.clothes.entity.code;

import jakarta.persistence.*;

@Entity
public class Type {

    @Id
    @Column(name = "type_code")
    private Long code;

    @ManyToOne
    @JoinColumn(name = "upper_type_code")
    private UpperType upperTypeCode;

    private String typeName;

}
