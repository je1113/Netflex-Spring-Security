package fast.campus.netflix.movie;

import java.util.List;

public class TmdbPageableMovies {
    private final List<TmdbMovie> tmdbMovies;
    private final int page;
    private final boolean hasNext;

    public TmdbPageableMovies(List<TmdbMovie> tmdbMovies, int page, boolean hasNext) {
        this.tmdbMovies = tmdbMovies;
        this.page = page;
        this.hasNext = hasNext;
    }
}
