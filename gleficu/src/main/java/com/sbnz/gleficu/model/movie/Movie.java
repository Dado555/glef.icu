package com.sbnz.gleficu.model.movie;

import com.sbnz.gleficu.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "movie_dbs")
@Entity
public class Movie extends BaseEntity {
    @Column(name = "created_at", nullable = true)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at", nullable = true)
    private OffsetDateTime deletedAt;

    @Column(name="type")
    private String type;

    @Column(name="title")
    private String title;

    @Column(name="year")
    private String year;

    @Column(name="rated")
    private String rated;

    @Column(name="released")
    private String released;

    @Column(name="runtime")
    private String runtime;

    @Column(name="genre")
    private String genre;

    @Column(name="director")
    private String director;

    @Column(name="writer")
    private String writer;

    @Column(name="actors")
    private String actors;

    @Column(name="plot")
    private String plot;

    @Column(name="language")
    private String language;

    @Column(name="country")
    private String country;

    @Column(name="poster")
    private String poster;

    @Column(name="imdb_rating")
    private String imdbRating;

    @Column(name="metascore")
    private String metascore;

    @Column(name="imdb_votes")
    private String imdbVotes;

    @Column(name="imdb_id")
    private String imdbID;

    @Column(name="torrent_links")
    private String torrentLinks;

    @Column(name="title_links")
    private String titleLinks;
}
