package com.dadada.onecloset.domain.clothes.repository;

import com.dadada.onecloset.domain.clothes.entity.Clothes;
import com.dadada.onecloset.domain.clothes.entity.Hashtag;
import com.dadada.onecloset.domain.clothes.entity.Tpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TpoRepository extends JpaRepository<Tpo, Long> {

    List<Tpo> findByClothes(Clothes clothes);

}
