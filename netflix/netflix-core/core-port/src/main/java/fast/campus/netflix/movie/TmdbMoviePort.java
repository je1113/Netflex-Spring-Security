package fast.campus.netflix.movie;

public interface TmdbMoviePort {
    TmdbPageableMovies fetchPageable(int page);
}
