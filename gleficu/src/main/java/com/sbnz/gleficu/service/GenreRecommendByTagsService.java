package com.sbnz.gleficu.service;

import com.sbnz.gleficu.model.RecommendRequest;
import com.sbnz.gleficu.model.User;
import com.sbnz.gleficu.model.facts.GenresRecommendByInputTagsFact;
import com.sbnz.gleficu.model.phases.GenreRecommendByInputTagsPhase;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreRecommendByTagsService {

    private final KieContainer kieContainer;

    @Autowired
    public GenreRecommendByTagsService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    public GenresRecommendByInputTagsFact recommendByTags() {
        RecommendRequest request = new RecommendRequest();
        request.setId(0);
        User user = new User();
        request.setUser(user);

        GenreRecommendByInputTagsPhase phase = new GenreRecommendByInputTagsPhase();
        // phase.setFavTags(Arrays.asList("hero", "combat"));
        phase.setFavTag("hero");
        phase.setRecommendId(request.getId());

        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(request);
        FactHandle factHandle = kieSession.insert(phase);
        kieSession.getAgenda().getAgendaGroup("Preporuka zanra").setFocus();
        kieSession.fireAllRules();

        GenresRecommendByInputTagsFact fact = (GenresRecommendByInputTagsFact) kieSession.getObjects(new ClassObjectFilter(GenresRecommendByInputTagsFact.class))
                .stream().findFirst().orElse(null);
        kieSession.delete(factHandle);
        kieSession.dispose();
        return fact;
    }
}
