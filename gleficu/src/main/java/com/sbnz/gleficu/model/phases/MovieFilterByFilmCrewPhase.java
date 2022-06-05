package com.sbnz.gleficu.model.phases;

import com.sbnz.gleficu.model.FilmCrew;
import com.sbnz.gleficu.model.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieFilterByFilmCrewPhase {
    List<FilmCrew> actors;
    List<FilmCrew> directors;
    List<FilmCrew> writers;
    List<Movie> movies;
}
