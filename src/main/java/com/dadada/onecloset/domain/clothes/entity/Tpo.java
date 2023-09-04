package com.dadada.onecloset.domain.clothes.entity;

import com.dadada.onecloset.domain.clothes.entity.type.TpoType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tpo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tpo_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "clothes_id")
    private Clothes clothes;

    @Enumerated(EnumType.STRING)
    private TpoType tpoName;

    @Builder
    public Tpo(Clothes clothes, TpoType tpo) {
        this.clothes = clothes;
        this.tpoName = tpo;
    }
}
