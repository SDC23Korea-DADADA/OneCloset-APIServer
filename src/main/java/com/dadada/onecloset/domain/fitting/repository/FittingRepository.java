package com.dadada.onecloset.domain.fitting.repository;

import com.dadada.onecloset.domain.fitting.entity.Fitting;
import com.dadada.onecloset.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FittingRepository extends JpaRepository<Fitting, Long> {

    Optional<Fitting> findByIdAndUser(Long id, User user);

    List<Fitting> findByUser(User user);

    List<Fitting> findByWearingAtMonth(String month);

}
