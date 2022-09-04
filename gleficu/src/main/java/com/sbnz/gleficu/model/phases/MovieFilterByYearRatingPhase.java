package com.sbnz.gleficu.model.phases;

import com.sbnz.gleficu.model.movie.Movie;
import com.sbnz.gleficu.model.enums.AgeRange;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieFilterByYearRatingPhase {
    List<Movie> movies;
    Double ratingLimit;
    AgeRange userAge;
    Integer yearLimit;
}
