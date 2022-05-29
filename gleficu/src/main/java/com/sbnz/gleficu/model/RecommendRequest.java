package com.sbnz.gleficu.model;

import com.sbnz.gleficu.model.enums.AgeRange;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table
@Entity
public class RecommendRequest extends BaseEntity{
    @ManyToOne
    private User user;

    @OneToMany
    private List<Tag> inputTags;

    @ManyToMany
    private List<Movie> allMovies;

    @Column(name = "ageRange", nullable = false)
    private AgeRange ageRange;
}
