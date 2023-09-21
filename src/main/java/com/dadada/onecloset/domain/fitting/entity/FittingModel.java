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
    private String labelMap;
    private String skeleton;
    private String keypoint;
    private String dense;
    private String denseNpz;
    private Boolean status;

    @Builder
    FittingModel(User user, FastApiModelRegistResponseDto responseDto, String originImg) {
        this.user = user;
        this.originImg = originImg;
        this.status = true;
    }

    public void updateInfo(FastApiModelRegistResponseDto responseDto) {
        this.labelMap = responseDto.getLabelMap();
        this.skeleton = responseDto.getSkeleton();
        this.keypoint = responseDto.getKeypoint();
        this.dense = responseDto.getDense();
        this.denseNpz = responseDto.getDenseNpz();
    }

    public void deleteModel() {
        this.status = false;
    }

}
