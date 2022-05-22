package com.sbnz.gleficu.model;

import com.sbnz.gleficu.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User extends BaseEntity{
    private Integer years;
    private Gender gender;
    private List<String> favouriteTags;

    private List<Movie> watched;
    private List<Movie> rated;
    private List<Movie> wishlist;
}
