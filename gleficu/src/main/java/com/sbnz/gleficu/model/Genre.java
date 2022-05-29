package com.sbnz.gleficu.model;

import com.sbnz.gleficu.model.enums.MovieGenre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "genre")
public class Genre extends BaseEntity{
    @Column(name = "genre", nullable = false)
    public MovieGenre genre;

    @ManyToMany(fetch = FetchType.EAGER)
    public List<Tag> tags;
}
