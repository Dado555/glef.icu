package com.sbnz.gleficu.model.phases;

import com.sbnz.gleficu.model.Genre;
import com.sbnz.gleficu.model.enums.AgeRange;
import com.sbnz.gleficu.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenresByAgeAndGender {
    Map<AgeRange, List<Genre>> byAge;
    Map<Gender, List<Genre>> byGender;
}
