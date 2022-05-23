package com.sbnz.gleficu.service;

import com.sbnz.gleficu.model.RecommendRequest;
import com.sbnz.gleficu.model.User;
import com.sbnz.gleficu.model.facts.GenresRecommendByTagsFact;
import com.sbnz.gleficu.model.phases.GenreRecommendByTagsPhase;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@Service
public class GenreRecommendByTagsService {

    private final KieContainer kieContainer;

    @Autowired
    public GenreRecommendByTagsService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    public GenresRecommendByTagsFact recommendByTags() {
        RecommendRequest request = new RecommendRequest();
        request.setId(0);
        User user = new User();
        request.setUser(user);

        GenreRecommendByTagsPhase phase = new GenreRecommendByTagsPhase();
        // phase.setFavTags(Arrays.asList("hero", "combat"));
        phase.setFavTag("hero");
        phase.setRecommendId(request.getId());

        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(request);
        FactHandle factHandle = kieSession.insert(phase);
        kieSession.getAgenda().getAgendaGroup("Preporuka zanra").setFocus();
        kieSession.fireAllRules();

        GenresRecommendByTagsFact fact = (GenresRecommendByTagsFact) kieSession.getObjects(new ClassObjectFilter(GenresRecommendByTagsFact.class))
                .stream().findFirst().orElse(null);
        kieSession.delete(factHandle);
        kieSession.dispose();
        return fact;
    }
}
