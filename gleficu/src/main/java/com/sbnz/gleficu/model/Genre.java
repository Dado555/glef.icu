package com.sbnz.gleficu.model;

import com.sbnz.gleficu.model.enums.MovieGenre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Genre extends BaseEntity{
    @Column(name = "genre", nullable = false)
    public MovieGenre genre;

    @ManyToMany
    public List<Tag> tags;
}
