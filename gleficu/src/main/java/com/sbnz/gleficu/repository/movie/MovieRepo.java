package com.sbnz.gleficu.repository.movie;

import com.sbnz.gleficu.model.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepo extends JpaRepository<Movie, Integer> {
    @Query("select movie from Movie movie where movie.genre like ?1")
    List<Movie> getAllByGenreSimilarTo(String genre);

    Movie getByImdbID(String imdbId);
}
