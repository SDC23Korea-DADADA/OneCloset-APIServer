package com.dadada.onecloset.domain.fitting.entity;

import com.dadada.onecloset.domain.user.entity.User;
import com.dadada.onecloset.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Fitting extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fitting_id")
    private Long id;

    private String fittingImg;

    private String fittingThumnailImg;

    private String wearingAtMonth;
    private String wearingAtDay;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fitting_model_id")
    private FittingModel fittingModel;

    @OneToMany(mappedBy = "fitting", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<FittingClothes> fittingClothesList;

    @Builder
    Fitting(FittingModel fittingModel, String fittingImg, String fittingThumnailImg, String wearingAtDay, String wearingAtMonth, User user) {
        this.fittingModel = fittingModel;
        this.fittingImg = fittingImg;
        this.fittingThumnailImg = fittingThumnailImg;
        this.wearingAtDay = wearingAtDay;
        this.wearingAtMonth = wearingAtMonth;
        this.user = user;
    }

    public void editWearingAt(String wearingAtMonth, String wearingAtDay) {
        this.wearingAtMonth = wearingAtMonth;
        this.wearingAtDay = wearingAtDay;
    }
}
