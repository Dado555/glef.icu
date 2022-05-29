package com.sbnz.gleficu.repository;

import com.sbnz.gleficu.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepo extends JpaRepository<Genre, Integer> {
}
