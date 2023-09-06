package com.dadada.onecloset.domain.user.repository;

import com.dadada.onecloset.domain.user.entity.User;
import com.dadada.onecloset.domain.user.entity.type.LoginType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginIdAndLoginType(String loginId, LoginType loginType);

}
