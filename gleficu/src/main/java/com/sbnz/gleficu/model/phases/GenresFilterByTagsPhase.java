package com.sbnz.gleficu.model.phases;

import com.sbnz.gleficu.model.Genre;
import com.sbnz.gleficu.model.movie.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class GenresFilterByTagsPhase extends BasePhase{
    private List<Tag> tags;
    private List<Genre> genres;
}
