package com.sbnz.gleficu.model.phases;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WatchedMoviePhase {
    Integer genreId;
    Long dateWatched;
}
