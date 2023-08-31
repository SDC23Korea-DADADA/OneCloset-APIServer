package com.dadada.onecloset.domain.user.repository;

import com.dadada.onecloset.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 카카오 이면서 loginId가 존재하는거
    Optional<User> findByLoginId(String loginId);

}
