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
@Entity
@Table(name = "crew")
public class FilmCrew extends BaseEntity{
    @Column(name = "fullName", nullable = false)
    public String fullName;

    @Column(name="biography", nullable = false)
    public String biography;

    @Column(name = "type", nullable = false)
    public CrewType crewType;
}
