package com.dadada.onecloset.domain.fitting.entity;

import com.dadada.onecloset.domain.codi.entity.CodiClothes;
import com.dadada.onecloset.domain.fitting.entity.code.Mannequin;
import com.dadada.onecloset.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Fitting {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fitting_id")
    private Long id;

    private String fittingImg;

    private String fittingThumnailImg;

    private LocalDateTime wearingAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mannequin_code")
    private Mannequin mannequin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "fitting", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<FittingClothes> fittingClothesList;

    @Builder Fitting(Mannequin mannequin, String fittingImg, String fittingThumnailImg, LocalDateTime wearingAt, User user) {
        this.mannequin = mannequin;
        this.fittingImg = fittingImg;
        this.fittingThumnailImg = fittingThumnailImg;
        this.wearingAt = wearingAt;
        this.user = user;
    }

    public void editWearingAt(LocalDateTime wearingAt) {
        this.wearingAt = wearingAt;
    }
}
