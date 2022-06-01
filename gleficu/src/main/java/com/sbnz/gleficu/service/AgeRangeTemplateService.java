package com.sbnz.gleficu.service;

import com.sbnz.gleficu.model.AgeRangeTemplate;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.drools.template.ObjectDataCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class AgeRangeTemplateService {
    private final RulesHandler rulesHandler;

    @Autowired
    public AgeRangeTemplateService(RulesHandler rulesHandler) {
        this.rulesHandler = rulesHandler;
    }

    public void createAgeRange(List<AgeRangeTemplate> ageRangeTemplateList) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream templateResource = classLoader.getResourceAsStream("templates/ageTemplate.drt");

        ObjectDataCompiler objectDataCompiler = new ObjectDataCompiler();

        String content = objectDataCompiler.compile(ageRangeTemplateList, templateResource);

        try {
            this.rulesHandler.evaluate(content, "status");
        } catch (IOException | MavenInvocationException e) {
            e.printStackTrace();
        }

        System.out.println(content);
    }
}
