package com.dadada.onecloset.domain.clothes.repository;

import com.dadada.onecloset.domain.clothes.entity.Clothes;
import com.dadada.onecloset.domain.clothes.entity.Hashtag;
import com.dadada.onecloset.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    @Query("SELECT DISTINCT h.hashtagName FROM Hashtag h WHERE h.user=:user")
    List<String> findByUserDistinctHashtagName(@Param("user")User user);

    List<Hashtag> findByClothes(Clothes clothes);

}
