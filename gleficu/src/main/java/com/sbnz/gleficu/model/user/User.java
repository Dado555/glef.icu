package com.sbnz.gleficu.model.user;
import com.sbnz.gleficu.model.enums.AgeRange;
import com.sbnz.gleficu.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer age;
    private Gender gender;
    private AgeRange ageRange;
    private String favouriteTags;
}
