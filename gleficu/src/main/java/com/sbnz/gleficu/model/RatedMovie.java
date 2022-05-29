package com.sbnz.gleficu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ratedMovie")
public class RatedMovie extends BaseEntity{
    @OneToOne
    public Movie movie;

    @Column(name = "rating", nullable = false)
    public Double rating;

    @Column(name = "date", nullable = false)
    public Long dateRated;
}
