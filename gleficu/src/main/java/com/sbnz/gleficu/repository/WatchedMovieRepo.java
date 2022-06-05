package com.sbnz.gleficu.repository;

import com.sbnz.gleficu.model.WatchedMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchedMovieRepo extends JpaRepository<WatchedMovie, Integer> {
    List<WatchedMovie> findAllByUserId(Integer userId);
}
