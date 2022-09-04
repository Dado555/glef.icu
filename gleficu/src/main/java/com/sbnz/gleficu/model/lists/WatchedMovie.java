package com.sbnz.gleficu.model.lists;

import com.sbnz.gleficu.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "watchlist_items")
public class WatchedMovie extends BaseEntity {
    @Column(name = "created_at", nullable = true)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at", nullable = true)
    private OffsetDateTime deletedAt;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "title")
    private String title;

    @Column(name = "genre")
    private String genre;

    @Column(name = "imdb_id")
    private String imdbId;

    @Column(name = "poster")
    private String poster;

    @Column(name = "imdb_rating")
    private String imdbRating;

    @Column(name = "released")
    private String released;
}
