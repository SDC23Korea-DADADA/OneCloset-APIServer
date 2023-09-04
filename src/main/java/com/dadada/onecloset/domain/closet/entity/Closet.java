package com.dadada.onecloset.domain.closet.entity;

import com.dadada.onecloset.domain.user.entity.User;
import com.dadada.onecloset.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Closet extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "closet_id")
    private Long id;

    private String name;

    private Integer icon;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "closet", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ClosetClothes> closetClothesList;

    @Builder
    public Closet(String name, User user, int icon) {
        this.name = name;
        this.user = user;
        this.icon = icon;
    }

    public void editInfo(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

}