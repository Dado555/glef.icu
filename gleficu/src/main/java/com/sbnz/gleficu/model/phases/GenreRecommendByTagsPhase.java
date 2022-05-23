package com.sbnz.gleficu.model.phases;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class GenreRecommendByTagsPhase extends BasePhase{
    private Integer recommendId;
    // private List<String> favTags;
    private String favTag;
}
