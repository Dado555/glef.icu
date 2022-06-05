package com.sbnz.gleficu.model.facts;

import com.sbnz.gleficu.model.Movie;
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
    Set<Movie> movies;
}
