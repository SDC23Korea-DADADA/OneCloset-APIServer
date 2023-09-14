package com.dadada.onecloset.domain.clothes.entity.code;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
public class Type {

    @Id
    @Column(name = "type_code")
    private Long code;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "upper_type_code")
    private UpperType upperTypeCode;

    private String typeName;

}
