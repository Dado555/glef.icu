package com.sbnz.gleficu.model;

import com.sbnz.gleficu.model.enums.MovieGenre;
import com.sbnz.gleficu.model.movie.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Genre extends BaseEntity{
    public MovieGenre genre;
    public List<Tag> tags;
}
