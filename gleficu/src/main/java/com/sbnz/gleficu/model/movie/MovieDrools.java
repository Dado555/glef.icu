package com.sbnz.gleficu.model.movie;

import com.sbnz.gleficu.model.FilmCrew;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDrools {
    Integer id;
    List<FilmCrew> actors;
    List<FilmCrew> director;
    List<FilmCrew> writer;
    Integer releaseYear;
    Double criticsRating;
}
