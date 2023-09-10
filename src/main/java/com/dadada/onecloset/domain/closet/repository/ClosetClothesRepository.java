package com.dadada.onecloset.domain.closet.repository;

import com.dadada.onecloset.domain.closet.entity.Closet;
import com.dadada.onecloset.domain.closet.entity.ClosetClothes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClosetClothesRepository extends JpaRepository<ClosetClothes, Long> {

    List<ClosetClothes> findByCloset(Closet closet);

}
