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

    @ManyToOne
    @JoinColumn(name = "color_code")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "type_code")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "material_code")
    private Material material;

    @ColumnDefault("true")
    private Boolean isRegisted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "clothes", fetch = FetchType.LAZY)
    private List<ClosetClothes> closetClothesList;

    @OneToMany(mappedBy = "clothes", cascade = CascadeType.ALL)
    private List<Hashtag> hashtagList;

    @OneToMany(mappedBy = "clothes", cascade = CascadeType.ALL)
    private List<Tpo> tpoList;

    @OneToMany(mappedBy = "clothes", cascade = CascadeType.ALL)
    private List<Weather> weatherList;

    @Builder
    public Clothes(String originImg, String thumnailImg, String description, Color color, Type type, Material material) {
        this.originImg = originImg;
        this.thumnailImg = thumnailImg;
        this.description = description;
        this.color = color;
        this.type = type;
        this.material = material;
    }

    public void deleteClothes() {
        this.isRegisted = false;
    }

    public void editDescription(String description) {
        this.description = description;
    }

}
