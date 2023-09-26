package com.dadada.onecloset.domain.clothes.repository;

import com.dadada.onecloset.domain.clothes.entity.Clothes;
import com.dadada.onecloset.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClothesRepository extends JpaRepository<Clothes, Long> {

    @Query("SELECT clothes FROM Clothes clothes where clothes.user=:user AND clothes.isRegisted = true")
    List<Clothes> findByUserWhereIsRegistIsTrue(@Param("user") User user);

    @Query("SELECT clothes FROM Clothes clothes where clothes.user=:user AND clothes.id=:id AND clothes.isRegisted = true")
    Optional<Clothes> findByIdAndUserWhereIsRegistIsTrue(@Param("id")Long id, @Param("user") User user);

    Optional<Clothes> findByIdAndUser(Long id, User user);

    List<Clothes> findByIsUseData(Boolean isUseData);

}
