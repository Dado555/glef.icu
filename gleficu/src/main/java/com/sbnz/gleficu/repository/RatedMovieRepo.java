package com.sbnz.gleficu.repository;

import com.sbnz.gleficu.model.RatedMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatedMovieRepo extends JpaRepository<RatedMovie, Integer> {
}
