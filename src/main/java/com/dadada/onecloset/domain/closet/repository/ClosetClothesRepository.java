package com.dadada.onecloset.domain.closet.repository;

import com.dadada.onecloset.domain.closet.entity.Closet;
import com.dadada.onecloset.domain.closet.entity.ClosetClothes;
import com.dadada.onecloset.domain.clothes.entity.Clothes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClosetClothesRepository extends JpaRepository<ClosetClothes, Long> {

    List<ClosetClothes> findByCloset(Closet closet);

    Optional<ClosetClothes> findByClosetAndClothes(Closet closet, Clothes clothes);

}
