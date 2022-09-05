package com.sbnz.gleficu.model.facts;

import com.sbnz.gleficu.model.movie.Movie;
import com.sbnz.gleficu.model.movie.MovieDrools;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MoviesFilterYearRatingFact {
    Set<MovieDrools> movies;
}
