package com.sbnz.gleficu.repository;

import com.sbnz.gleficu.model.FilmCrew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmCrewRepo extends JpaRepository<FilmCrew, Integer> {
}
