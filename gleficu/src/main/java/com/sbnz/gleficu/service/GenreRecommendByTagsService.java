package com.sbnz.gleficu.service;

import com.sbnz.gleficu.model.Genre;
import com.sbnz.gleficu.model.RecommendRequest;
import com.sbnz.gleficu.model.User;
import com.sbnz.gleficu.model.facts.GenresFilterByTagsFact;
import com.sbnz.gleficu.model.phases.GenresFilterByTagsPhase;
import com.sbnz.gleficu.repository.GenreRepo;
import com.sbnz.gleficu.repository.RequestRepo;
import com.sbnz.gleficu.repository.UserRepo;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class GenreRecommendByTagsService {

    private final KieContainer kieContainer;

    private final RequestRepo requestRepo;

    private final GenreRepo genreRepo;

    private final UserRepo userRepo;

    @Autowired
    public GenreRecommendByTagsService(KieContainer kieContainer, RequestRepo requestRepo, GenreRepo genreRepo, UserRepo userRepo) {
        this.kieContainer = kieContainer;
        this.requestRepo = requestRepo;
        this.genreRepo = genreRepo;
        this.userRepo = userRepo;
    }

    public int recommendByInputTags(Integer requestId) {
        RecommendRequest request = this.requestRepo.findById(requestId).orElseThrow(() ->
                new RuntimeException("Request not found"));

        GenresFilterByTagsPhase phase = new GenresFilterByTagsPhase();
        phase.setTags(request.getInputTags());
        // phase.setFavTag("hero");
        phase.setRecommendId(request.getId());
        List<Genre> genres = genreRepo.findAll();
        phase.setGenres(genres);

        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(request);
        FactHandle factHandle = kieSession.insert(phase);
        kieSession.getAgenda().getAgendaGroup("Lista zanrova na osnovu unesenih tagova").setFocus();
        kieSession.fireAllRules();

        Collection<GenresFilterByTagsFact> facts = (Collection<GenresFilterByTagsFact>)
                kieSession.getObjects(new ClassObjectFilter(GenresFilterByTagsFact.class));

        GenresFilterByTagsPhase phase2 = new GenresFilterByTagsPhase();
        User user = this.userRepo.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        phase2.setTags(user.getFavouriteTags());

        for (GenresFilterByTagsFact fact : facts) {
            for (Genre g : fact.getPossibleGenres()) {
                System.out.println(g.getGenre().toString());
            }
            System.out.println(fact.getPossibleGenres().size());
        }
//        phase2.setGenres();
        kieSession.delete(factHandle);

        kieSession.dispose();
        return facts.size();
    }
}
