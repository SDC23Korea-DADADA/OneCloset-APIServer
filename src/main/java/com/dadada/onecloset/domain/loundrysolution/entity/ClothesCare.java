package com.dadada.onecloset.domain.loundrysolution.entity;

import com.dadada.onecloset.domain.clothes.entity.code.Material;
import com.dadada.onecloset.domain.clothes.entity.code.Type;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class ClothesCare {

    @Id
    @Column(name = "clothes_care_code")
    private Long code;

    @ManyToOne
    @JoinColumn(name = "material_code")
    private Material materialCode;

    @ManyToOne
    @JoinColumn(name = "type_code")
    private Type typeCode;

    private String loundryCourse;

    private String dryerCourse;

    private String airDresserCourse;

    @OneToMany(mappedBy = "clothesCare", fetch = FetchType.LAZY)
    private List<CareTip> careTipList;

    @OneToMany(mappedBy = "clothesCare", fetch = FetchType.LAZY)
    private List<LoundryTip> loundryTipList;

}
