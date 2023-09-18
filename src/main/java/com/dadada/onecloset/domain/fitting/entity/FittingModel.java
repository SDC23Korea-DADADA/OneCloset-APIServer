package com.dadada.onecloset.domain.fitting.entity;

import com.dadada.onecloset.domain.clothes.entity.code.Color;
import com.dadada.onecloset.domain.user.dto.FittingModelRegistRequestDto;
import com.dadada.onecloset.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FittingModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fitting_model_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String segmentation;

    private String poseSkeleton;

    private String keypoints;

    private String denseModel;

    @Builder
    FittingModel(User user, String segmentation, String poseSkeleton, String keypoints, String denseModel) {

        this.user = user;
        this.segmentation = segmentation;
        this.poseSkeleton = poseSkeleton;
        this.keypoints = keypoints;
        this.denseModel = denseModel;

    }

}
