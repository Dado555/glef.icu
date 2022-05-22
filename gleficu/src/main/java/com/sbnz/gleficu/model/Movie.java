package com.sbnz.gleficu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Movie extends BaseEntity{
    public Double rating;
    public List<String> tags;
    public List<String> genres;
    public List<String> actors;
    public String language;
    public Integer releaseYear;
    public String director;
    public String writer;
}
