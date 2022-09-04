package com.sbnz.gleficu.repository.movie;

import com.sbnz.gleficu.model.movie.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepo extends JpaRepository<Tag, Integer> {
    List<Tag> findAllByName(String name);

    @Query("select tag from Tag tag where tag.genre like ?1")
    List<Tag> findAllByGenreName(String genre);
}
