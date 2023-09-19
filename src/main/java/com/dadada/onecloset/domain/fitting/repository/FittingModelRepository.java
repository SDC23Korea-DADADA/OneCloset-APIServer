package com.dadada.onecloset.domain.fitting.repository;

import com.dadada.onecloset.domain.fitting.entity.FittingModel;
import com.dadada.onecloset.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FittingModelRepository extends JpaRepository<FittingModel, Long> {

    @Query("SELECT model FROM FittingModel model where model.user=:user AND model.status = true")
    List<FittingModel> findByUserWhereStatusIsTrue(@Param("user")User user);

    @Query("SELECT model FROM FittingModel model where model.user=:user AND model.id = :modelId AND model.status = true")
    Optional<FittingModel> findByIdAndUserWhereStatusIsTrue(@Param("modelId") Long modelId, @Param("user")User user);

}
