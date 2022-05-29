package com.sbnz.gleficu.repository;

import com.sbnz.gleficu.model.WishlistMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistMovieRepo extends JpaRepository<WishlistMovie, Integer> {
}
