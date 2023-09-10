package com.dadada.onecloset.domain.clothes.repository;

import com.dadada.onecloset.domain.clothes.entity.Clothes;
import com.dadada.onecloset.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClothesRepository extends JpaRepository<Clothes, Long> {

    List<Clothes> findByUser(User user);

}
