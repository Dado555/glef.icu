package com.sbnz.gleficu.model;

import com.sbnz.gleficu.model.enums.AgeRange;
import com.sbnz.gleficu.model.movie.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "request")
@Entity
public class RecommendRequest extends BaseEntity{
    @Column(name = "userId", nullable = false)
    private Integer userId;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Tag> inputTags;

    @Column(name = "ageRange", nullable = false)
    private AgeRange ageRange;
}
