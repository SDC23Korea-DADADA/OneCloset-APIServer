package com.dadada.onecloset.domain.closet.repository;

import com.dadada.onecloset.domain.closet.entity.Closet;
import com.dadada.onecloset.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClosetRepository extends JpaRepository<Closet, Long> {

    List<Closet> findByUser(User user);

    Optional<Closet> findByIdAndUser(Long id, User user);

}
