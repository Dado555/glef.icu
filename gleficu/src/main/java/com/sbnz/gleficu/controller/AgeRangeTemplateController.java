package com.sbnz.gleficu.controller;

import com.sbnz.gleficu.model.AgeRangeTemplate;
import com.sbnz.gleficu.service.AgeRangeTemplateService;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public void recommendByTags(@RequestBody List<AgeRangeTemplate> ageRangeTemplates) throws MavenInvocationException, IOException {
        ageRangeTemplateService.createAgeRange(ageRangeTemplates);
    }
}
