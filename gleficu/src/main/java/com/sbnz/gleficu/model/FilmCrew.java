package com.sbnz.gleficu.model;

import com.sbnz.gleficu.model.enums.CrewType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilmCrew extends BaseEntity{
    public String fullName;
    public String biography;
}
