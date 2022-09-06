package com.sbnz.gleficu.service;

import com.sbnz.gleficu.model.FilmCrew;
import com.sbnz.gleficu.model.Genre;
import com.sbnz.gleficu.model.RecommendRequest;
import com.sbnz.gleficu.model.enums.AgeRange;
import com.sbnz.gleficu.model.enums.Gender;
import com.sbnz.gleficu.model.enums.MovieGenre;
import com.sbnz.gleficu.model.facts.*;
import com.sbnz.gleficu.model.lists.WatchedMovie;
import com.sbnz.gleficu.model.lists.WishlistMovie;
import com.sbnz.gleficu.model.movie.Movie;
import com.sbnz.gleficu.model.movie.MovieDrools;
import com.sbnz.gleficu.model.movie.Tag;
import com.sbnz.gleficu.model.phases.*;
import com.sbnz.gleficu.model.user.User;
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

import java.util.*;

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

    public List<Movie> recommendByInputTags(Integer userId, String tags) {
        // 1. inputTag & favTag
        Optional<UserDb> currentUser = userRepo.findById(userId);
        List<Genre> genres = prepareGenres();

        User user = new User(
                currentUser.get().getAge(),
                currentUser.get().getGender().equals("female") ? Gender.FEMALE : Gender.MALE,
                AgeRange.YOUNG,
                currentUser.get().getFavouriteTags()
        );

        RecommendRequest request = new RecommendRequest();
        request.setUserId(userId);
        request.setId(userId);
        request.setAgeRange(prepareAgeRange(user.getAge()));
        request.setInputTags(prepareTags(tags));

        GenresFilterByTagsPhase phase = new GenresFilterByTagsPhase();
        phase.setTags(request.getInputTags());
        phase.setRecommendId(userId);
        phase.setUserId(userId);
        phase.setGenres(genres);

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
        phase2.setTags(prepareTags(user.getFavouriteTags()));
        phase2.setRecommendId(request.getId());
        phase2.setGenres(phase2Genres);

        FactHandle factHandle2 = kieSession.insert(phase2);
        kieSession.getAgenda().getAgendaGroup("Lista zanrova na osnovu omiljenih tagova").setFocus();
        kieSession.fireAllRules();

        Collection<GenresFilterByFavTagsFact> facts2 = (Collection<GenresFilterByFavTagsFact>)
                kieSession.getObjects(new ClassObjectFilter(GenresFilterByFavTagsFact.class));

        kieSession.delete(factHandle2);

        List<Genre> phase3Genres = new ArrayList<>();
        for (GenresFilterByFavTagsFact fact : facts2) {
            for (Genre g : fact.getPossibleGenres()) {
                phase3Genres.add(g);
                System.out.println(g.getGenre().toString());
            }
            System.out.println(fact.getPossibleGenres().size());
        }

        // Postavljanje AgeRange
        kieSession.insert(user);
        kieSession.getAgenda().getAgendaGroup("Postavljanje starosne dobi").setFocus();
        kieSession.fireAllRules();
        System.out.println(user.getAgeRange());

        // 2. age & gender

        GenresFilterAgeGenderPhase phase3 = new GenresFilterAgeGenderPhase();
        phase3.setGenres(genres);
        phase3.setGender(user.getGender());
        if(user.getAgeRange() == null) {
            System.out.println("AGE RANGE not set!");
            phase3.setAgeRange(AgeRange.YOUNG);
        } else
            phase3.setAgeRange(user.getAgeRange());

        kieSession.insert(user);
        FactHandle factHandle3 = kieSession.insert(phase3);
        kieSession.getAgenda().getAgendaGroup("Filter zanrova po polu i godinama").setFocus();
        kieSession.fireAllRules();

        Collection<GenresFilterByAgeAndGenderFact> facts3 = (Collection<GenresFilterByAgeAndGenderFact>)
                kieSession.getObjects(new ClassObjectFilter(GenresFilterByAgeAndGenderFact.class));

        kieSession.delete(factHandle3);

        List<Genre> phase4Genres = new ArrayList<>();
        for (GenresFilterByAgeAndGenderFact fact : facts3) {
            for (Genre g : fact.getPossibleGenres()) {
                phase4Genres.add(g);
                System.out.println(g.getGenre().toString());
            }
            System.out.println(fact.getPossibleGenres().size());
        }

        // 3. watched & wishlist
        List<WatchedMovie> userWatchedMovies = watchedMovieRepo.findAllByUserId(currentUser.get().getId());
        List<WishlistMovie> userWishlistMovies = wishlistMovieRepo.findAllByUserId(currentUser.get().getId());

        GenresFilterRatedWatchedWishlistPhase phase4 = new GenresFilterRatedWatchedWishlistPhase();
        phase4.setGenres(genres);
        phase4.setGenresWatched(userWatchedMovies);
        phase4.setGenresWishlist(userWishlistMovies);

        // kieSession = kieContainer.newKieSession();
        FactHandle factHandle4 = kieSession.insert(phase4);
        kieSession.getAgenda().getAgendaGroup("Filter zanrova liste korisnickih filmova").setFocus();
        kieSession.fireAllRules();

        Collection<GenresFilterByRatedWatchedWishlist> facts4 = (Collection<GenresFilterByRatedWatchedWishlist>)
                kieSession.getObjects(new ClassObjectFilter(GenresFilterByRatedWatchedWishlist.class));

        kieSession.delete(factHandle4);

        Set<Genre> phase5Genres = new HashSet<>();
        List<Integer> addedLists = new ArrayList<>();
        for (GenresFilterByRatedWatchedWishlist fact : facts4) {
            for (Genre g : fact.getPossibleGenres()) {
                if(!addedLists.contains(g.getId())) {
                    addedLists.add(g.getId());
                    phase5Genres.add(g);
                }
                System.out.println(g.getGenre().toString());
            }
            System.out.println(fact.getPossibleGenres().size());
        }

        // 4. all combined -> final list of genres
        GenresFilterByAgeAndGenderFact agFact = facts3.stream().findFirst().orElseThrow(() ->
                new RuntimeException("Fact not found"));
//        GenresFilterByRatedWatchedWishlist rwwFact = facts4.stream().findFirst().orElseThrow(() ->
//                new RuntimeException("Fact not found"));
        GenresFilterByFavTagsFact favTagsFact = facts2.stream().findFirst().orElseThrow(() ->
                new RuntimeException("Fact not found"));

        GenresFinalPhase genresFinalPhase = new GenresFinalPhase();
        genresFinalPhase.setAgeGenres(agFact.getPossibleGenres());
        genresFinalPhase.setListsGenres(phase5Genres);
        genresFinalPhase.setTagsGenres(favTagsFact.getPossibleGenres());

        FactHandle factHandle5 =  kieSession.insert(genresFinalPhase);
        kieSession.getAgenda().getAgendaGroup("Finalno zanrovi").setFocus();
        kieSession.fireAllRules();

        Collection<GenresFilterFinalFact> facts5 = (Collection<GenresFilterFinalFact>)
                kieSession.getObjects(new ClassObjectFilter(GenresFilterFinalFact.class));

        kieSession.delete(factHandle5);

        List<Genre> phase6Genres = new ArrayList<>();
        for (GenresFilterFinalFact fact : facts5) {
            System.out.println(fact.getPercentage().toString() + "% preporuka");
            for (Genre g : fact.getGenres()) {
                phase6Genres.add(g);
                System.out.println(g.getGenre().toString());
            }
            System.out.println(fact.getGenres().size());
        }

        System.out.println("GENRES FINAL: ");
        System.out.println(phase6Genres.size());
        for(Genre g: phase6Genres) {
            System.out.println(g.getName());
        }

        List<Movie> finalMovieList = new ArrayList<>();
        if(userWatchedMovies.size() > 0 || userWishlistMovies.size() > 0) {
            List<MovieDrools> moviesDrools = prepareMovies(phase6Genres);
            MovieFilterByFilmCrewPhase moviePhase1 = new MovieFilterByFilmCrewPhase();
            moviePhase1.setMovies(moviesDrools);
            List<FilmCrew> actors = new ArrayList<>();
            List<FilmCrew> directors = new ArrayList<>();
            List<FilmCrew> writers = new ArrayList<>();
            for(WatchedMovie movie: userWatchedMovies) {
                Movie temp = movieRepo.getByImdbID(movie.getImdbId());
                actors.addAll(getFilmCrew(temp.getActors()));
                directors.addAll(getFilmCrew(temp.getDirector()));
                writers.addAll(getFilmCrew(temp.getWriter()));
            }
            for(WishlistMovie movie: userWishlistMovies) {
                Movie temp = movieRepo.getByImdbID(movie.getImdbId());
                actors.addAll(getFilmCrew(temp.getActors()));
                directors.addAll(getFilmCrew(temp.getDirector()));
                writers.addAll(getFilmCrew(temp.getWriter()));
            }
            moviePhase1.setActors(actors);
            moviePhase1.setDirectors(directors);
            moviePhase1.setWriters(writers);

            FactHandle movieFactHandle1 = kieSession.insert(moviePhase1);
            kieSession.getAgenda().getAgendaGroup("Filter filmova po filmskoj ekipi").setFocus();
            kieSession.fireAllRules();

            Collection<MoviesFilterFilmCrewFact> movieFact1 = (Collection<MoviesFilterFilmCrewFact>)
                    kieSession.getObjects(new ClassObjectFilter(MoviesFilterFilmCrewFact.class));

            kieSession.delete(movieFactHandle1);

            List<MovieDrools> moviePhase1List = new ArrayList<>();
            List<Integer> added1 = new ArrayList<>();
            for (MoviesFilterFilmCrewFact fact : movieFact1) {
                for (MovieDrools m : fact.getMovies()) {
                    if(!added1.contains(m.getId())) {
                        added1.add(m.getId());
                        moviePhase1List.add(m);
                        System.out.println(m.getReleaseYear());
                    }
                }
                System.out.println(fact.getMovies().size());
            }

            MovieFilterByYearRatingPhase moviePhase2 = new MovieFilterByYearRatingPhase();
            moviePhase2.setMovies(moviePhase1List);
            moviePhase2.setUserAge(request.getAgeRange());
            moviePhase2.setYearLimit(2020);

            FactHandle movieFactHandle2 = kieSession.insert(moviePhase2);
            kieSession.getAgenda().getAgendaGroup("Filter filmova godine i ocjena").setFocus();
            kieSession.fireAllRules();

            Collection<MoviesFilterYearRatingFact> movieFact2 = (Collection<MoviesFilterYearRatingFact>)
                    kieSession.getObjects(new ClassObjectFilter(MoviesFilterYearRatingFact.class));

            kieSession.delete(movieFactHandle2);

            List<MovieDrools> moviePhase2List = new ArrayList<>();
            List<Integer> added2 = new ArrayList<>();
            for (MoviesFilterYearRatingFact fact : movieFact2) {
                for (MovieDrools m : fact.getMovies()) {
                    if(!added2.contains(m.getId())) {
                        added2.add(m.getId());
                        moviePhase2List.add(m);
                        System.out.println(m.getReleaseYear());
                    }
                }
                System.out.println(fact.getMovies().size());
            }

            for(MovieDrools md: moviePhase2List) {
                Movie m = movieRepo.getById(md.getId());
                finalMovieList.add(m);
            }
        } else {
            finalMovieList = getMoviesByGenres(phase6Genres);
        }

        // kieSession.dispose();
        return finalMovieList;
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

    private List<Movie> getMoviesByGenres(List<Genre> genres) {
        List<Movie> retMovies = new ArrayList<>();
        List<Integer> added = new ArrayList<>();
        for(Genre g: genres) {
            List<Movie> dbMovies = movieRepo.getAllByGenreSimilarTo("%"+g.getName()+"%");
            for(Movie m: dbMovies) {
                if(!added.contains(m.getId())) {
                    added.add(m.getId());
                    retMovies.add(m);
                }
            }
        }
        return retMovies;
    }

    private List<MovieDrools> prepareMovies(List<Genre> genres) {
        List<MovieDrools> retMovies = new ArrayList<>();
        List<Integer> added = new ArrayList<>();
        for(Genre g: genres) {
            List<Movie> dbMovies = movieRepo.getAllByGenreSimilarTo("%"+g.getName()+"%");
            for(Movie m: dbMovies) {
                if(!added.contains(m.getId())) {
                    added.add(m.getId());
                    retMovies.add(new MovieDrools(m.getId(), getFilmCrew(m.getActors()), getFilmCrew(m.getDirector()),
                            getFilmCrew(m.getWriter()), Integer.parseInt(m.getYear()), Double.parseDouble(m.getImdbRating())));
                }
            }
        }
        return retMovies;
    }

    private List<FilmCrew> getFilmCrew(String filmCrew) {
        List<FilmCrew> filmCrewList = new ArrayList<>();
        for(String crew: filmCrew.split(",")) {
            filmCrewList.add(new FilmCrew(crew.trim(), ""));
        }
        return filmCrewList;
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
