package com.sbnz.gleficu.model;

import com.sbnz.gleficu.model.enums.MovieLanguage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "movie")
@Entity
public class Movie extends BaseEntity{
    @Column(name = "criticsRating", nullable = false)
    public Double criticsRating;

    @ManyToMany
    public List<Tag> tags;

    @ManyToMany
    public List<Genre> genres;

    @ManyToMany
    public List<FilmCrew> actors;

    @Column(name = "language", nullable = false)
    public MovieLanguage language;

    @Column(name = "releaseYear", nullable = false)
    public Integer releaseYear;

    @ManyToOne
    public FilmCrew director;

    @ManyToMany
    public List<FilmCrew> writer;
}
