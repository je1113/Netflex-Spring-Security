package fast.campus.netflix.movie;

import fast.campus.netflix.movie.response.MovieResponse;
import fast.campus.netflix.movie.response.PageableMoviesResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class MovieService implements FetchMovieUseCase, InsertMovieUseCase{
    private final TmdbMoviePort tmdbMoviePort;
    PersistenceMoviePort persistenceMoviePort;

    public MovieService(TmdbMoviePort tmdbMoviePort) {
        this.tmdbMoviePort = tmdbMoviePort;
    }

    @Override
    public PageableMoviesResponse fetchFromClient(int page) {
        TmdbPageableMovies tmdbPageableMovies = tmdbMoviePort.fetchPageable(page);
        return new PageableMoviesResponse(
                tmdbPageableMovies.getTmdbMovies().stream()
                        .map(movie -> new MovieResponse(
                                movie.getMovieName(),
                                movie.getIsAdult(),
                                movie.getGenre(),
                                movie.getOverview(),
                                movie.getReleaseAt()
                        )).collect(Collectors.toList()),
                tmdbPageableMovies.getPage(),
                tmdbPageableMovies.isHasNext()
        );
    }

    @Override
    public PageableMoviesResponse fetchFromDb(int page) {
        return null;
    }

    @Override
    public void insert(List<NetflixMovie> movies) {
        movies.forEach(persistenceMoviePort::insert);
    }
}
