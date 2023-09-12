package com.dadada.onecloset.domain.clothes.repository;

import com.dadada.onecloset.domain.clothes.entity.code.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeRepository extends JpaRepository<Type, Long> {

    Optional<Type> findByTypeName(String type);

}
