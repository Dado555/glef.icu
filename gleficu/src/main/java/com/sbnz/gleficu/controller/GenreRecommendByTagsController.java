package com.sbnz.gleficu.controller;

import com.sbnz.gleficu.model.facts.GenresRecommendByInputTagsFact;
import com.sbnz.gleficu.service.GenreRecommendByTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/recommend/genre-by-tags")
public class GenreRecommendByTagsController {
    private final GenreRecommendByTagsService genreRecommendByTagsService;

    @Autowired
    public GenreRecommendByTagsController(GenreRecommendByTagsService service) {
        this.genreRecommendByTagsService = service;
    }

    @GetMapping
    public GenresRecommendByInputTagsFact recommendByTags() { // @RequestBody GenreRecommendByTagsPhase phase
        return genreRecommendByTagsService.recommendByTags();
    }
}
