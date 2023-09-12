package com.dadada.onecloset.domain.codi.entity;

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
public class Codi {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codi_id")
    private Long id;

    private String originImg;

    private String thumnailImg;

    private String description;

    private LocalDateTime wearingAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "codi", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<CodiClothes> codiClothesList;

    @Builder
    public Codi(String originImg, String thumnailImg, String description, LocalDateTime wearingAt, User user) {
        this.originImg = originImg;
        this.thumnailImg = thumnailImg;
        this.description = description;
        this.wearingAt = wearingAt;
        this.user = user;
    }

    public void editDescription(String description) {
        this.description = description;
    }

    public void editWearingAt(LocalDateTime wearingAt) {
        this.wearingAt = wearingAt;
    }

}
