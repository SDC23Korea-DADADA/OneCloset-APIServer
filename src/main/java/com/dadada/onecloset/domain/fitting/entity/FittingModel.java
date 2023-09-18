package com.dadada.onecloset.domain.fitting.entity;

import com.dadada.onecloset.domain.user.entity.User;
import com.dadada.onecloset.fastapi.FastApiModelRegistResponseDto;
import com.dadada.onecloset.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FittingModel extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fitting_model_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String originImg;

    private String segmentation;

    private String poseSkeleton;

    private String keypoints;

    private String denseModel;

    @Builder
    FittingModel(User user, FastApiModelRegistResponseDto responseDto) {
        this.user = user;
        this.originImg = responseDto.getOriginImg();
        this.segmentation = responseDto.getSegmentation();
        this.poseSkeleton = responseDto.getSkeleton();
        this.keypoints = responseDto.getKeypoints();
        this.denseModel = responseDto.getDenseModel();
    }

}
