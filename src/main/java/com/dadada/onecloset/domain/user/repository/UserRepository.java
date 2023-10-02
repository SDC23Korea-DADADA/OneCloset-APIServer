package com.dadada.onecloset.domain.user.repository;

import com.dadada.onecloset.domain.clothes.entity.code.Type;
import com.dadada.onecloset.domain.user.entity.User;
import com.dadada.onecloset.domain.user.entity.type.LoginType;
import com.dadada.onecloset.domain.user.entity.type.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginIdAndLoginType(String loginId, LoginType loginType);

    @Query("SELECT user FROM User user where user.loginId=:loginId AND user.loginType=:loginType AND user.status = true")
    Optional<User> findByLoginIdAndLoginTypeWhereStatusIsTrue(
            @Param("loginId")String loginId, @Param("loginType")LoginType loginType
    );

    @Query("SELECT user FROM User user where user.id=:userId AND user.status = true")
    Optional<User> findByIdWhereStatusIsTrue(@Param("userId") Long userId);

    @Query("SELECT user FROM User user where user.id=:userId AND user.role=:role")
    Optional<User> findByIdWhereRoleIsAdmin(@Param("userId") Long userId, @Param("role") Role role);

}
