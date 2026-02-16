package fast.campus.netflix.repository.movie;

import fast.campus.netflix.entity.movie.MovieEntity;
import fast.campus.netflix.movie.NetflixMovie;
import fast.campus.netflix.movie.PersistenceMoviePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MovieRepository implements PersistenceMoviePort {

    private final MovieJpaRepository movieJpaRepository;

    @Override
    public List<NetflixMovie> fetchBy(int page, int size) {
        return movieJpaRepository.search(PageRequest.of(page, size))
                .stream()
                .map(MovieEntity::toDomain)
                .toList();
    }

    @Override
    public NetflixMovie findBy(String movieName) {
        return movieJpaRepository.findByMovieName(movieName)
                .map(MovieEntity::toDomain)
                .orElseThrow();
    }

    @Override
    public String insert(NetflixMovie netflixMovie) {
        MovieEntity entity = MovieEntity.toEntity(netflixMovie);
        Optional<MovieEntity> byMovieName = movieJpaRepository.findByMovieName(netflixMovie.getMovieName());

        if(byMovieName.isEmpty()){
            log.info("신규 영화 추가{}", netflixMovie);
            movieJpaRepository.save(entity);
            return entity.getMovieId();
        }
        return null;
    }
}
