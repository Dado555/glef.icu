package com.sbnz.gleficu.model;

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
@Table
public class FilmCrew extends BaseEntity{
    @Column(name = "fullName", nullable = false)
    public String fullName;

    @Column(name="biography", nullable = false)
    public String biography;
}
