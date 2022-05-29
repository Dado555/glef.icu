package com.sbnz.gleficu.service;

import com.sbnz.gleficu.model.RecommendRequest;
import com.sbnz.gleficu.model.facts.GenresRecommendByInputTagsFact;
import com.sbnz.gleficu.model.phases.GenreRecommendByInputTagsPhase;
import com.sbnz.gleficu.repository.GenreRepo;
import com.sbnz.gleficu.repository.RequestRepo;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GenreRecommendByTagsService {

    private final KieContainer kieContainer;

    private final RequestRepo requestRepo;

    private final GenreRepo genreRepo;

    @Autowired
    public GenreRecommendByTagsService(KieContainer kieContainer, RequestRepo requestRepo, GenreRepo genreRepo) {
        this.kieContainer = kieContainer;
        this.requestRepo = requestRepo;
        this.genreRepo = genreRepo;
    }

    public int recommendByInputTags(Integer requestId) {
        RecommendRequest request = this.requestRepo.findById(requestId).orElseThrow(() ->
                new RuntimeException("Request not found"));

        GenreRecommendByInputTagsPhase phase = new GenreRecommendByInputTagsPhase();
        phase.setInputTags(request.getInputTags());
        // phase.setFavTag("hero");
        phase.setRecommendId(request.getId());
        phase.setGenres(genreRepo.findAll());

        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(request);
        FactHandle factHandle = kieSession.insert(phase);
        kieSession.getAgenda().getAgendaGroup("Lista zanrova na osnovu unesenih tagova").setFocus();
        kieSession.fireAllRules();

        Collection<GenresRecommendByInputTagsFact> facts = (Collection<GenresRecommendByInputTagsFact>)
                kieSession.getObjects(new ClassObjectFilter(GenresRecommendByInputTagsFact.class));
        kieSession.delete(factHandle);
        kieSession.dispose();
        return facts.size();
    }
}
