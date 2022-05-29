package com.sbnz.gleficu.model.facts;

import com.sbnz.gleficu.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class GenresFilterByTagsFact {
    private Set<Genre> possibleGenres;
}
