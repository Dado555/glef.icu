package com.sbnz.gleficu.service;

import com.sbnz.gleficu.model.Genre;
import com.sbnz.gleficu.model.RecommendRequest;
import com.sbnz.gleficu.model.enums.AgeRange;
import com.sbnz.gleficu.model.enums.MovieGenre;
import com.sbnz.gleficu.model.facts.GenresFilterByFavTagsFact;
import com.sbnz.gleficu.model.facts.GenresFilterByInputTagsFact;
import com.sbnz.gleficu.model.lists.WatchedMovie;
import com.sbnz.gleficu.model.lists.WishlistMovie;
import com.sbnz.gleficu.model.movie.Movie;
import com.sbnz.gleficu.model.movie.Tag;
import com.sbnz.gleficu.model.phases.GenresFilterByTagsPhase;
import com.sbnz.gleficu.model.user.UserDb;
import com.sbnz.gleficu.repository.lists.WatchedMovieRepo;
import com.sbnz.gleficu.repository.lists.WishlistMovieRepo;
import com.sbnz.gleficu.repository.movie.MovieRepo;
import com.sbnz.gleficu.repository.movie.TagRepo;
import com.sbnz.gleficu.repository.user.UserRepo;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class GenreRecommendByTagsService {

    private final KieSession kieSession;

    private final UserRepo userRepo;

    private final WatchedMovieRepo watchedMovieRepo;

    private final WishlistMovieRepo wishlistMovieRepo;

    private final MovieRepo movieRepo;

    private final TagRepo tagRepo;

    @Autowired
    public GenreRecommendByTagsService(KieSession kieSession, UserRepo userRepo,
                                       MovieRepo movieRepo, WatchedMovieRepo watchedMovieRepo,
                                       WishlistMovieRepo wishlistMovieRepo, TagRepo tagRepo) {
        this.kieSession = kieSession;
        this.userRepo = userRepo;
        this.watchedMovieRepo = watchedMovieRepo;
        this.wishlistMovieRepo = wishlistMovieRepo;
        this.movieRepo = movieRepo;
        this.tagRepo = tagRepo;
    }

    public int recommendByInputTags(Integer userId, String tags) {
//        System.out.println(userId);
//        System.out.println(tags);
//        // System.out.println(String.join(",", ", tags));
//        for(UserDb u : userRepo.findAll()) {
//            System.out.println(u.getUsername());
//        }
//        for(WatchedMovie wm: watchedMovieRepo.findAll()){
//            System.out.println(wm.getId());
//        }
//        for(WishlistMovie wm : wishlistMovieRepo.findAll()) {
//            System.out.println(wm.getId());
//        }
//        for(Movie m : movieRepo.findAll()) {
//            System.out.println(m.getId());
//        }

        // 1. inputTag & favTag
        Optional<UserDb> currentUser = userRepo.findById(userId);

        RecommendRequest request = new RecommendRequest();
        request.setUserId(userId);
        request.setId(userId);
        request.setAgeRange(prepareAgeRange(currentUser.get().getAge()));
        request.setInputTags(prepareTags(tags));

        GenresFilterByTagsPhase phase = new GenresFilterByTagsPhase();
        phase.setTags(request.getInputTags());
        phase.setRecommendId(userId);
        phase.setUserId(userId);
        phase.setGenres(prepareGenres());

        kieSession.insert(request);
        FactHandle factHandle = kieSession.insert(phase);
        kieSession.getAgenda().getAgendaGroup("Lista zanrova na osnovu unesenih tagova").setFocus();
        kieSession.fireAllRules();

        Collection<GenresFilterByInputTagsFact> facts = (Collection<GenresFilterByInputTagsFact>)
                kieSession.getObjects(new ClassObjectFilter(GenresFilterByInputTagsFact.class));

        kieSession.delete(factHandle);

        List<Genre> phase2Genres = new ArrayList<>();
        for (GenresFilterByInputTagsFact fact : facts) {
            for (Genre g : fact.getPossibleGenres()) {
                phase2Genres.add(g);
                System.out.println(g.getGenre().toString());
            }
            System.out.println(fact.getPossibleGenres().size());
        }

        GenresFilterByTagsPhase phase2 = new GenresFilterByTagsPhase();
        phase2.setTags(prepareTags(currentUser.get().getFavouriteTags()));
        phase2.setRecommendId(request.getId());
        phase2.setGenres(phase2Genres);

        FactHandle factHandle2 = kieSession.insert(phase2);
        kieSession.getAgenda().getAgendaGroup("Lista zanrova na osnovu omiljenih tagova").setFocus();
        kieSession.fireAllRules();

        Collection<GenresFilterByFavTagsFact> facts2 = (Collection<GenresFilterByFavTagsFact>)
                kieSession.getObjects(new ClassObjectFilter(GenresFilterByFavTagsFact.class));

        kieSession.delete(factHandle2);
        kieSession.dispose();

        List<Genre> phase3Genres = new ArrayList<>();
        for (GenresFilterByFavTagsFact fact : facts2) {
            for (Genre g : fact.getPossibleGenres()) {
                phase3Genres.add(g);
                System.out.println(g.getGenre().toString());
            }
            System.out.println(fact.getPossibleGenres().size());
        }
//
//        // Postavljanje AgeRange
//        // kieSession = KieContainer.newKieSession();
//        kieSession.insert(user);
//        kieSession.getAgenda().getAgendaGroup("Postavljanje starosne dobi").setFocus();
//        kieSession.fireAllRules();
//
//        System.out.println(user.getAgeRange());
//
//        kieSession.dispose();
//
//        // 2. age & gender
//
//        GenresFilterAgeGenderPhase phase3 = new GenresFilterAgeGenderPhase();
//        phase3.setGenres(genres);
//        phase3.setGender(user.getGender());
//        if(user.getAgeRange() == null) {
//            System.out.println("AGE RANGE not set!");
//            phase3.setAgeRange(AgeRange.YOUNG);
//        } else
//            phase3.setAgeRange(user.getAgeRange());
//
//        // kieSession = kieContainer.newKieSession();
//        kieSession.insert(user);
//        FactHandle factHandle3 = kieSession.insert(phase3);
//        kieSession.getAgenda().getAgendaGroup("Filter zanrova po polu i godinama").setFocus();
//        kieSession.fireAllRules();
//
//        Collection<GenresFilterByAgeAndGenderFact> facts3 = (Collection<GenresFilterByAgeAndGenderFact>)
//                kieSession.getObjects(new ClassObjectFilter(GenresFilterByAgeAndGenderFact.class));
//
//        kieSession.delete(factHandle3);
//        kieSession.dispose();
//
//        List<Genre> phase4Genres = new ArrayList<>();
//        for (GenresFilterByAgeAndGenderFact fact : facts3) {
//            for (Genre g : fact.getPossibleGenres()) {
//                phase4Genres.add(g);
//                System.out.println(g.getGenre().toString());
//            }
//            System.out.println(fact.getPossibleGenres().size());
//        }
//
//        // 3. rated, watched & wishlist
//
//        GenresFilterRatedWatchedWishlistPhase phase4 = new GenresFilterRatedWatchedWishlistPhase();
//        phase4.setGenres(genres);
//        phase4.setGenresRated(ratedMovieRepo.getRatedMovieByUserId(user.getId()));
//        phase4.setGenresWatched(watchedMovieRepo.findAllByUserId(user.getId()));
//        phase4.setGenresWishlist(wishlistMovieRepo.findAllByUserId(user.getId()));
//
//        // kieSession = kieContainer.newKieSession();
//        FactHandle factHandle4 = kieSession.insert(phase4);
//        kieSession.getAgenda().getAgendaGroup("Filter zanrova liste korisnickih filmova").setFocus();
//        kieSession.fireAllRules();
//
//        Collection<GenresFilterByRatedWatchedWishlist> facts4 = (Collection<GenresFilterByRatedWatchedWishlist>)
//                kieSession.getObjects(new ClassObjectFilter(GenresFilterByRatedWatchedWishlist.class));
//
//        kieSession.delete(factHandle4);
//        kieSession.dispose();
//
//        List<Genre> phase5Genres = new ArrayList<>();
//        for (GenresFilterByRatedWatchedWishlist fact : facts4) {
//            for (Genre g : fact.getPossibleGenres()) {
//                phase4Genres.add(g);
//                System.out.println(g.getGenre().toString());
//            }
//            System.out.println(fact.getPossibleGenres().size());
//        }
//
//        // 4. all combined -> final list of genres
//        // kieSession = kieContainer.newKieSession();
//        GenresFilterByAgeAndGenderFact agFact = facts3.stream().findFirst().orElseThrow(() ->
//                new RuntimeException("Fact not found"));
//        kieSession.insert(agFact);
//        GenresFilterByRatedWatchedWishlist rwwFact = facts4.stream().findFirst().orElseThrow(() ->
//                new RuntimeException("Fact not found"));
//        kieSession.insert(rwwFact);
//        GenresFilterByFavTagsFact favTagsFact = facts2.stream().findFirst().orElseThrow(() ->
//                new RuntimeException("Fact not found"));
//        kieSession.insert(favTagsFact);
//
//        kieSession.getAgenda().getAgendaGroup("Finalno zanrovi").setFocus();
//        kieSession.fireAllRules();
//
//        Collection<GenresFilterFinalFact> facts5 = (Collection<GenresFilterFinalFact>)
//                kieSession.getObjects(new ClassObjectFilter(GenresFilterFinalFact.class));
//
//        List<Genre> phase6Genres = new ArrayList<>();
//        for (GenresFilterFinalFact fact : facts5) {
//            System.out.println(fact.getPercentage().toString() + "% preporuka");
//            for (Genre g : fact.getGenres()) {
//                phase4Genres.add(g);
//                System.out.println(g.getGenre().toString());
//            }
//            System.out.println(fact.getGenres().size());
//        }
//
//        kieSession.dispose();
//        return facts.size();
        return 0;
    }

    private List<Tag> prepareTags(String tags) {
        List<Tag> tagList = new ArrayList<>();
        for(String tag : tags.split(",")) {
            List<Tag> found = tagRepo.findAllByName(tag.trim());
            if(found.size() > 0) {
                tagList.addAll(found);
            }
        }
        return tagList;
    }

    private AgeRange prepareAgeRange(Integer age) {
        if (age < 25) {
            return AgeRange.YOUNG;
        } else if (age <50) {
            return AgeRange.MIDDLE;
        } else {
            return AgeRange.OLDER;
        }
    }

    private List<Genre> prepareGenres() {
        String[] genres = {"Action", "Adult", "Adventure", "Animation", "Biography", "Comedy", "Crime",
                "Documentary", "Drama", "Family", "Fantasy", "Film Noir", "Game Show", "History", "Horror", "Musical",
                "Music", "Mystery", "News", "Reality-TV", "Romance", "Sci-Fi", "Short", "Sport", "Talk-Show",
                "Thriller", "War", "Western"};
        List<Genre> genresDb = new ArrayList<>();
        int i = 1;
        for(String genre : genres) {
            Genre gen = new Genre();
            gen.setId(i);
            gen.setGenre(MovieGenre.forInt(i));
            gen.setTags(tagRepo.findAllByGenreName("%"+genre+"%"));
            gen.setName(genre);
            i++;
            genresDb.add(gen);
        }
        return genresDb;
    }
}
