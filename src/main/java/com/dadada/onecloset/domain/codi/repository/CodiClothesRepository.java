package com.dadada.onecloset.domain.codi.repository;

import com.dadada.onecloset.domain.clothes.entity.Clothes;
import com.dadada.onecloset.domain.codi.entity.Codi;
import com.dadada.onecloset.domain.codi.entity.CodiClothes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodiClothesRepository extends JpaRepository<CodiClothes, Long> {

    List<CodiClothes> findByCodi(Codi codi);

}
