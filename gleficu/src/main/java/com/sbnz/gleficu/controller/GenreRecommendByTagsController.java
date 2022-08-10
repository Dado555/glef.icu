package com.sbnz.gleficu.controller;import org.kie.api.builder.KieScanner;


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

    @GetMapping(value = "/{requestId}")
    public int recommendByTags(@PathVariable Integer requestId) {
        return genreRecommendByTagsService.recommendByInputTags(requestId);
    }
}
