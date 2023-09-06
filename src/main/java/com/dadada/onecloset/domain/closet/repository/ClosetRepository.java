package com.dadada.onecloset.domain.closet.repository;

import com.dadada.onecloset.domain.closet.entity.Closet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClosetRepository extends JpaRepository<Closet, Long> {
    
}
