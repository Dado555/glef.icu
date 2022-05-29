package com.sbnz.gleficu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table
@Entity
public class Movie extends BaseEntity{
    @Column(name = "criticsRating", nullable = false)
    public Double criticsRating;

    @ManyToMany
    public List<Tag> tags;

    @ManyToMany
    public List<Genre> genres;

    @ManyToMany
    public List<Actor> actors;

    @Column(name = "language", nullable = false)
    public String language;

    @Column(name = "releaseYear", nullable = false)
    public Integer releaseYear;

    @ManyToOne
    public Director director;

    @ManyToMany
    public List<Writer> writer;
}
