package com.sbnz.gleficu.model.phases;

import com.sbnz.gleficu.model.FilmCrew;
import com.sbnz.gleficu.model.Genre;
import com.sbnz.gleficu.model.RatedMovie;
import com.sbnz.gleficu.model.lists.WatchedMovie;
import com.sbnz.gleficu.model.lists.WishlistMovie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenresFilterRatedWatchedWishlistPhase {
    List<Genre> genres;
    List<RatedMoviePhase> genresRated;
    List<WatchedMoviePhase> genresWatched;
    List<WishlistMoviePhase> genresWishlist;

    public void setGenresRated(List<RatedMovie> ratedMovies) {
        List<RatedMoviePhase> ratedGenres = new ArrayList<>();
        for(RatedMovie rm : ratedMovies) {
            ratedGenres.add(new RatedMoviePhase(rm.getId(), rm.getRating()));
        }
        this.genresRated = ratedGenres;
    }

    public void setGenresWatched(List<WatchedMovie> watchedMovies) {
        List<WatchedMoviePhase> watchedMoviePhases = new ArrayList<>();
        for(WatchedMovie rm : watchedMovies) {
            List<String> genres = new ArrayList<>();
            for(String gen: rm.getGenre().split(",")) {
                genres.add(gen.trim());
            }
            watchedMoviePhases.add(new WatchedMoviePhase(genres));
        }
        this.genresWatched = watchedMoviePhases;
    }

    public void setGenresWishlist(List<WishlistMovie> wishlistMovies) {
        List<WishlistMoviePhase> wishlistMoviePhases = new ArrayList<>();
        for(WishlistMovie wm: wishlistMovies) {
            List<String> genres = new ArrayList<>();
            for(String gen: wm.getGenre().split(",")) {
                genres.add(gen.trim());
            }
            wishlistMoviePhases.add(new WishlistMoviePhase(genres));
        }
        this.genresWishlist = wishlistMoviePhases;
    }
}
