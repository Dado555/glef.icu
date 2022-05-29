package com.sbnz.gleficu;

import com.sbnz.gleficu.model.*;
import com.sbnz.gleficu.model.enums.*;
import com.sbnz.gleficu.repository.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;

@Component
public class DbInitializer implements ApplicationRunner {

    private final GenreRepo genreRepo;

    private final MovieRepo movieRepo;

    private final RatedMovieRepo ratedMovieRepo;

    private final RequestRepo requestRepo;

    private final TagRepo tagRepo;

    private final UserRepo userRepo;

    private final WatchedMovieRepo watchedMovieRepo;

    private final WishlistMovieRepo wishlistMovieRepo;

    private final FilmCrewRepo filmCrewRepo;

    public DbInitializer(GenreRepo genreRepo, MovieRepo movieRepo, RatedMovieRepo ratedMovieRepo,
                         RequestRepo requestRepo, TagRepo tagRepo, UserRepo userRepo, FilmCrewRepo filmCrewRepo,
                         WatchedMovieRepo watchedMovieRepo, WishlistMovieRepo wishlistMovieRepo) {
        this.genreRepo = genreRepo;
        this.movieRepo = movieRepo;
        this.ratedMovieRepo = ratedMovieRepo;
        this.requestRepo = requestRepo;
        this.tagRepo = tagRepo;
        this.userRepo = userRepo;
        this.watchedMovieRepo = watchedMovieRepo;
        this.wishlistMovieRepo = wishlistMovieRepo;
        this.filmCrewRepo = filmCrewRepo;
    }


    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        FilmCrew actor1 = new FilmCrew("Fullname1", "Biography1", CrewType.ACTOR);
        FilmCrew actor2 = new FilmCrew("Fullname2", "Biography2", CrewType.ACTOR);
        FilmCrew actor3 = new FilmCrew("Fullname3", "Biography3", CrewType.ACTOR);
        FilmCrew actor4 = new FilmCrew("Fullname4", "Biography4", CrewType.ACTOR);
        FilmCrew actor5 = new FilmCrew("Fullname5", "Biography5", CrewType.ACTOR);
        FilmCrew actor6 = new FilmCrew("Fullname6", "Biography6", CrewType.ACTOR);

        FilmCrew director1 = new FilmCrew("Fullname7", "Biography7", CrewType.DIRECTOR);
        FilmCrew director2 = new FilmCrew("Fullname8", "Biography8", CrewType.DIRECTOR);
        FilmCrew director3 = new FilmCrew("Fullname9", "Biography9", CrewType.DIRECTOR);

        FilmCrew writer1 = new FilmCrew("Fullname10", "Biography10", CrewType.WRITER);
        FilmCrew writer2 = new FilmCrew("Fullname11", "Biography11", CrewType.WRITER);
        FilmCrew writer3 = new FilmCrew("Fullname12", "Biography12", CrewType.WRITER);

        filmCrewRepo.saveAll(Arrays.asList(actor1, actor2, actor3, actor4, actor5, actor6,
                director1, director2, director3, writer1, writer2, writer3));

        Tag t1 = new Tag(MovieTag.ACTION_HERO);
        Tag t2 = new Tag(MovieTag.CHASE);
        Tag t3 = new Tag(MovieTag.CAR_CHASE);
        Tag t4 = new Tag(MovieTag.VILLAIN);
        Tag t5 = new Tag(MovieTag.TOUGH_GUY);

        tagRepo.saveAll(Arrays.asList(t1, t2, t3, t4, t5));

        Genre g1 = new Genre(MovieGenre.ACTION, Arrays.asList(t2, t3, t5));
        Genre g2 = new Genre(MovieGenre.FANTASY, Arrays.asList(t1, t4));
        Genre g3 = new Genre(MovieGenre.ADVENTURE, Arrays.asList(t1, t2, t3));

        genreRepo.saveAll(Arrays.asList(g1, g2, g3));

        Movie m1 = new Movie(7.0, Arrays.asList(t1, t4, t2), Arrays.asList(g1, g2),
                Arrays.asList(actor1, actor2), MovieLanguage.ENGLISH, 1999,
                director1, Arrays.asList(writer1, writer2));
        Movie m2 = new Movie(7.5, Arrays.asList(t3, t5), Arrays.asList(g3),
                Arrays.asList(actor3, actor4), MovieLanguage.ENGLISH, 2001,
                director2, Arrays.asList(writer3));
        Movie m3 = new Movie(8.0, Arrays.asList(t1, t2, t3, t4, t5), Arrays.asList(g1, g2, g3),
                Arrays.asList(actor1, actor3, actor5), MovieLanguage.ENGLISH, 2010,
                director3, Arrays.asList(writer1, writer3));

        movieRepo.saveAll(Arrays.asList(m1, m2, m3));

        RatedMovie rm1 = new RatedMovie(m1, 8.0, -1L);
        RatedMovie rm2 = new RatedMovie(m2, 7.0, -1L);
        RatedMovie rm3 = new RatedMovie(m3, 9.0, -1L);

        ratedMovieRepo.saveAll(Arrays.asList(rm1, rm2, rm3));

        WatchedMovie wm1 = new WatchedMovie(m1, -1L);
        WatchedMovie wm2 = new WatchedMovie(m2, -1L);
        WatchedMovie wm3 = new WatchedMovie(m3, -1L);

        watchedMovieRepo.saveAll(Arrays.asList(wm1, wm2, wm3));

        WishlistMovie wsh1 = new WishlistMovie(m1, -1L);
        WishlistMovie wsh2 = new WishlistMovie(m2, -1L);
        WishlistMovie wsh3 = new WishlistMovie(m3, -1L);

        wishlistMovieRepo.saveAll(Arrays.asList(wsh1, wsh2, wsh3));

        User u1 = new User("mail", "pass", 22, Gender.MALE, Arrays.asList(t1, t4),
                Arrays.asList(wm1), Arrays.asList(rm1), Arrays.asList(wsh2));

        // userRepo.saveAll(Arrays.asList(u1));
        u1 = userRepo.save(u1);

        RecommendRequest request = new RecommendRequest(u1.getId(), Arrays.asList(t2, t5), AgeRange.YOUNG);

        requestRepo.saveAll(Arrays.asList(request));
    }
}
