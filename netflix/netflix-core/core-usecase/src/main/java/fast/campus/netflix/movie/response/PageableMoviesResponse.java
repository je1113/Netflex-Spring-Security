package fast.campus.netflix.movie.response;

import fast.campus.netflix.movie.response.MovieResponse;

import java.util.List;

public class PageableMoviesResponse {
    private final List<MovieResponse> tmdbMovies;
    private final int page;
    private final boolean hasNext;

    public PageableMoviesResponse(List<MovieResponse> tmdbMovies, int page, boolean hasNext) {
        this.tmdbMovies = tmdbMovies;
        this.page = page;
        this.hasNext = hasNext;
    }
}
