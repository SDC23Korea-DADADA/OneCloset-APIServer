package com.dadada.onecloset.domain.closet.entity;

import com.dadada.onecloset.domain.closet.dto.ClosetCreateRequestDto;
import com.dadada.onecloset.domain.closet.dto.ClosetEditRequestDto;
import com.dadada.onecloset.domain.user.entity.User;
import com.dadada.onecloset.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Closet extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "closet_id")
    private Long id;

    private String name;

    private Integer icon;

    private String iconColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "closet", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ClosetClothes> closetClothesList;

    @Builder
    public Closet(User user, ClosetCreateRequestDto requestDto) {
        this.user = user;
        this.name = requestDto.getName();
        this.icon = requestDto.getIcon();
        this.iconColor = requestDto.getColorCode();
    }

    public void editInfo(ClosetEditRequestDto requestDto) {
        this.name = requestDto.getName();
        this.icon = requestDto.getIcon();
        this.iconColor = requestDto.getColorCode();
    }

}