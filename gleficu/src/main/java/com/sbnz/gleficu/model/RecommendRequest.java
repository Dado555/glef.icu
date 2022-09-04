package com.sbnz.gleficu.model;

import com.sbnz.gleficu.model.enums.AgeRange;
import com.sbnz.gleficu.model.movie.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RecommendRequest extends BaseEntity{
    private Integer userId;
    private List<Tag> inputTags;
    private AgeRange ageRange;
}
