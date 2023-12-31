package com.dadada.onecloset.domain.user.entity;

import com.dadada.onecloset.domain.closet.entity.Closet;
import com.dadada.onecloset.domain.clothes.entity.Clothes;
import com.dadada.onecloset.domain.codi.entity.Codi;
import com.dadada.onecloset.domain.fitting.entity.Fitting;
import com.dadada.onecloset.domain.fitting.entity.FittingModel;
import com.dadada.onecloset.domain.user.entity.type.GenderType;
import com.dadada.onecloset.domain.user.entity.type.LoginType;
import com.dadada.onecloset.domain.user.entity.type.Role;
import com.dadada.onecloset.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String loginId;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String profileImg;

    @Column(nullable = false)
    private Boolean status;

    @Enumerated(EnumType.STRING)
    private GenderType genderType;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Closet> closetList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Clothes> clothesList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Codi> codiList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Fitting> fittingList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<FittingModel> fittingModelList;

    @Builder
    public User(String loginId, LoginType loginType, String nickname, String profileImg, String email) {
        this.loginId = loginId;
        this.loginType = loginType;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.email = email;
        this.role = Role.USER;
        this.genderType = GenderType.BLANK;
        this.status = true;
    }

    public void updateGender(GenderType genderType) {
        this.genderType = genderType;
    }

    public void leaveService() {
        this.status = false;
    }

    public void tempRejoin() {
        this.status = true;
    }
}
