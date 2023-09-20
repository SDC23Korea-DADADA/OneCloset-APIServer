package com.dadada.onecloset.domain.clothes.repository;

import com.dadada.onecloset.domain.clothes.entity.code.UpperType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UpperTypeRepository extends JpaRepository<UpperType, Long> {

    Optional<UpperType> findByUpperTypeName(String upperTypeName);

}
