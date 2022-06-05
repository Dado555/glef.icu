package com.sbnz.gleficu.model;

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
@Table(name = "watchedMovie")
public class WatchedMovie extends BaseEntity{
    @OneToOne
    public Movie movie;

    @ManyToOne
    public User user;

    @Column(name = "date", nullable = false)
    public Long dateWatched;
}
