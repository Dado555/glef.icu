package com.sbnz.gleficu.repository.movie;

import com.sbnz.gleficu.model.movie.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepo extends JpaRepository<Tag, Integer> {
}
