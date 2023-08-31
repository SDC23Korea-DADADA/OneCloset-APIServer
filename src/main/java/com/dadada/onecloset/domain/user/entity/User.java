package com.dadada.onecloset.domain.user.entity;

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
    private Long UserId;

    @Column(name = "login_id", nullable = false)
    private String loginId;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "profile_img", nullable = false)
    private String profileImg;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean status;

//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//    private List<Closet> closetList;
//
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//    private List<Clothes> clothesList;
//
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//    private  List<Codi> codiList;

    @Builder
    public User(String loginId, LoginType loginType, String nickname, String profileImg) {
        this.loginId = loginId;
        this.loginType = loginType;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }

    public void leaveService() {
        this.status = false;
    }
}
