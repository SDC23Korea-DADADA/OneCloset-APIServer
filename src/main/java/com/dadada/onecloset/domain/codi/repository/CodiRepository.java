package com.dadada.onecloset.domain.codi.repository;

import com.dadada.onecloset.domain.user.entity.User;
import com.dadada.onecloset.domain.codi.entity.Codi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodiRepository extends JpaRepository<Codi, Long> {

    Optional<Codi> findByIdAndUser(Long id, User user);

}
