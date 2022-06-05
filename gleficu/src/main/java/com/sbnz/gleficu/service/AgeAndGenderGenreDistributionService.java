package com.sbnz.gleficu.service;

import com.sbnz.gleficu.model.Genre;
import com.sbnz.gleficu.model.enums.AgeRange;
import com.sbnz.gleficu.model.enums.Gender;
import com.sbnz.gleficu.model.phases.GenresByAgeAndGender;
import com.sbnz.gleficu.model.phases.GenresDistributionTemplate;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.drools.template.ObjectDataCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AgeAndGenderGenreDistributionService {
    private final RulesHandler rulesHandler;

    @Autowired
    public AgeAndGenderGenreDistributionService(RulesHandler rulesHandler) {
        this.rulesHandler = rulesHandler;
    }

    public void setGenresByAgeAndGender(GenresByAgeAndGender genresDistribution) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream templateResource = classLoader.getResourceAsStream("templates/genreDistributionByGenderAndAgeTemplate.drt");

        ObjectDataCompiler objectDataCompiler = new ObjectDataCompiler();

//        GenresByAgeAndGender gwd = new GenresByAgeAndGender();
//        Map<Gender, List<Genre>> byGenders = new HashMap<>();
//        List<Genre> genreList = new ArrayList<>();
//        genreList.add(new Genre(MovieGenre.ACTION, new ArrayList<>()));
//        genreList.add(new Genre(MovieGenre.ADVENTURE, new ArrayList<>()));
//        byGenders.put(Gender.MALE, genreList);
//        byGenders.put(Gender.FEMALE, genreList);
//
//        Map<AgeRange, List<Genre>> byAge = new HashMap<>();
//        byAge.put(AgeRange.YOUNG, genreList);
//        byAge.put(AgeRange.MIDDLE, genreList);
//        byAge.put(AgeRange.OLDER, genreList);
//
//        gwd.setByGender(byGenders);
//        gwd.setByAge(byAge);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        try {
//            String json = objectMapper.writeValueAsString(gwd);
//            System.out.println(json);
//            System.out.println(objectMapper.writeValueAsString(byGenders));
//            System.out.println(objectMapper.writeValueAsString(byAge));
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

        List<GenresDistributionTemplate> templateList = new ArrayList<>();

        for (Map.Entry<AgeRange, List<Genre>> entry: genresDistribution.getByAge().entrySet()) {
            for (Map.Entry<Gender, List<Genre>> entry2: genresDistribution.getByGender().entrySet()) {
                List<Integer> byAge = new ArrayList<>();
                for (Genre g : entry.getValue()) {
                    byAge.add(g.getId());
                }

                List<Integer> byGender = new ArrayList<>();
                for (Genre g: entry2.getValue()) {
                    byGender.add(g.getId());
                }

                templateList.add(new GenresDistributionTemplate(byAge, byGender));
            }
        }

        String content = objectDataCompiler.compile(templateList, templateResource);

        try {
            this.rulesHandler.evaluate(content, "genreByGenderAndAge");
        } catch (IOException | MavenInvocationException e) {
            e.printStackTrace();
        }

        System.out.println(content);
    }
}
