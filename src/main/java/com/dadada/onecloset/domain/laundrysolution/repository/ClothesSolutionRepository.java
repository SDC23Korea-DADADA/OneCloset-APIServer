package com.dadada.onecloset.domain.laundrysolution.repository;

import com.dadada.onecloset.domain.clothes.entity.code.Material;
import com.dadada.onecloset.domain.clothes.entity.code.Type;
import com.dadada.onecloset.domain.laundrysolution.entity.ClothesCare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClothesSolutionRepository extends JpaRepository<ClothesCare, Long> {

    Optional<ClothesCare> findByMaterialCodeAndTypeCode(Material material, Type type);

}
