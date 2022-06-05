package com.sbnz.gleficu.model;

import com.sbnz.gleficu.model.enums.AgeRange;
import com.sbnz.gleficu.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Table(name = "customer")
@Entity
public class User extends BaseUser{
    @Column(name = "years", nullable = false)
    private Integer years;

    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name="ageRange")
    private AgeRange ageRange;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Tag> favouriteTags;

    public User(String email, String password, Integer years, Gender gender, List<Tag> favouriteTags) {
        super(email, password);
        this.years = years;
        this.gender = gender;
        this.favouriteTags = favouriteTags;
    }
}
