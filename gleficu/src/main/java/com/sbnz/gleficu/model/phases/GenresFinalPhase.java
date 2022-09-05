package com.sbnz.gleficu.model.phases;

import com.sbnz.gleficu.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenresFinalPhase {
    private Set<Genre> ageGenres;
    private Set<Genre> listsGenres;
    private Set<Genre> tagsGenres;
}
