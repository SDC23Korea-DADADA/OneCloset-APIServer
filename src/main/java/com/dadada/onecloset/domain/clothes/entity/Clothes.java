package com.dadada.onecloset.domain.clothes.entity;

import com.dadada.onecloset.domain.closet.entity.ClosetClothes;
import com.dadada.onecloset.domain.clothes.entity.code.Color;
import com.dadada.onecloset.domain.clothes.entity.code.Material;
import com.dadada.onecloset.domain.clothes.entity.code.Type;
import com.dadada.onecloset.domain.user.entity.User;
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
public class Clothes extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clothes_id")
    private Long id;

    @Column(nullable = false)
    private String originImg;

    @Column(nullable = false)
    private String thumnailImg;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_code")
    private Color color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_code")
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_code")
    private Material material;

    private Boolean isRegisted;

    private Boolean isUseData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "clothes", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ClosetClothes> closetClothesList;

    @OneToMany(mappedBy = "clothes", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Hashtag> hashtagList;

    @OneToMany(mappedBy = "clothes", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Tpo> tpoList;

    @OneToMany(mappedBy = "clothes", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Weather> weatherList;

    @Builder
    public Clothes(String originImg, String thumnailImg, User user, String description, Color color, Type type, Material material) {
        this.originImg = originImg;
        this.thumnailImg = thumnailImg;
        this.description = description;
        this.user = user;
        this.color = color;
        this.type = type;
        this.material = material;
        this.isRegisted = true;
        this.isUseData = false;
    }

    public void deleteClothes() {
        this.isRegisted = false;
    }

    public void restoreClothes() {
        this.isRegisted = true;
    }

    public void updateClothes(String description, Color color, Type type, Material material) {
        this.description = description;
        this.color = color;
        this.type = type;
        this.material = material;
    }

    public void updateUrl(String originImg, String thumnailImg) {
        this.originImg = originImg;
        this.thumnailImg = thumnailImg;
    }

    public void useClothesData(){
        this.isUseData = true;
    }

}
