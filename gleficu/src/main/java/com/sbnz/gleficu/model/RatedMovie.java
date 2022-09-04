package com.sbnz.gleficu.model;

import com.sbnz.gleficu.model.movie.Movie;
import com.sbnz.gleficu.model.user.UserDb;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ratedMovie")
public class RatedMovie extends BaseEntity{
    @OneToOne
    public Movie movie;

    @ManyToOne
    public UserDb user;

    @Column(name = "rating", nullable = false)
    public Double rating;

    @Column(name = "date", nullable = false)
    public Long dateRated;
}
