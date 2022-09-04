package com.sbnz.gleficu.repository.lists;

import com.sbnz.gleficu.model.lists.WishlistMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistMovieRepo extends JpaRepository<WishlistMovie, Integer> {
    List<WishlistMovie> findAllByUserId(Integer userId);
}
