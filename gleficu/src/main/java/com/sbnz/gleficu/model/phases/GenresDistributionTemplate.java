package com.sbnz.gleficu.model.phases;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenresDistributionTemplate {
    List<Integer> byAge;
    List<Integer> byGender;
}
