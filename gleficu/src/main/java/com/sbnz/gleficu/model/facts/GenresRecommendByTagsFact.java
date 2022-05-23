package com.sbnz.gleficu.model.facts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class GenresRecommendByTagsFact {
    // private List<String> possibleGenres;
    private String genre;
    private Integer recommendId;
}
