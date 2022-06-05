package com.sbnz.gleficu.controller;

import com.sbnz.gleficu.model.phases.GenresByAgeAndGender;
import com.sbnz.gleficu.service.AgeAndGenderGenreDistributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/recommend/genre-filter-by-age-gender")
public class GenreFilterByAgeAndGenderController {
    private final AgeAndGenderGenreDistributionService service;

    @Autowired
    public GenreFilterByAgeAndGenderController(AgeAndGenderGenreDistributionService service) {
        this.service = service;
    }

    @PostMapping()
    public void createGenresDistributionTemplate(@RequestBody GenresByAgeAndGender genres) {
        service.setGenresByAgeAndGender(genres);
    }
}
