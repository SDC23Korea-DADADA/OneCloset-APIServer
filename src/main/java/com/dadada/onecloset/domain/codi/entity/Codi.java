package com.dadada.onecloset.domain.codi.entity;

import com.dadada.onecloset.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Codi {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codi_id")
    private Long id;

    private String originImg;
    private String thumnailImg;
    private String wearingAtMonth;
    private String wearingAtDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "codi", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<CodiClothes> codiClothesList;

    @Builder
    public Codi(String originImg, String thumnailImg, String wearingAtMonth, String wearingAtDay, User user) {
        this.originImg = originImg;
        this.thumnailImg = thumnailImg;
        this.wearingAtMonth = wearingAtMonth;
        this.wearingAtDay = wearingAtDay;
        this.user = user;
    }

    public void editImage(String originImg, String thumnailImg) {
        this.originImg = originImg;
        this.thumnailImg = thumnailImg;
    }

    public void editWearingAt(String wearingAtDay, String wearingAtMonth) {
        this.wearingAtMonth = wearingAtMonth;
        this.wearingAtDay = wearingAtDay;
    }

}
