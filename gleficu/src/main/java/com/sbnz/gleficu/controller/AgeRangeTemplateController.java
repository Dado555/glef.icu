package com.sbnz.gleficu.controller;

import com.sbnz.gleficu.model.phases.AgeRangeTemplate;
import com.sbnz.gleficu.service.AgeRangeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/recommend/age-range-template")
public class AgeRangeTemplateController {
    private final AgeRangeTemplateService ageRangeTemplateService;

    @Autowired
    public AgeRangeTemplateController(AgeRangeTemplateService ageRangeTemplateService) {
        this.ageRangeTemplateService = ageRangeTemplateService;
    }

    @PostMapping()
    public void createAgeRangeTemplate(@RequestBody List<AgeRangeTemplate> ageRangeTemplates) {
        ageRangeTemplateService.createAgeRange(ageRangeTemplates);
    }
}
