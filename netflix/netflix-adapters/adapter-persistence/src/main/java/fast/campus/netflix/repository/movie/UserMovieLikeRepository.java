package fast.campus.netflix.repository.movie;

import fast.campus.netflix.entity.movie.UserMovieLikeEntity;
import fast.campus.netflix.movie.LikeMoviePort;
import fast.campus.netflix.movie.UserMovieLike;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserMovieLikeRepository implements LikeMoviePort {

    private final UserMovieLikeJpaRepository userMovieLikeJpaRepository;

    @Override
    @Transactional
    public UserMovieLike save(UserMovieLike domain) {
        UserMovieLikeEntity entity = UserMovieLikeEntity.toEntity(domain);
        return userMovieLikeJpaRepository.save(entity).toDomain();
    }

    @Override
    @Transactional
    public Optional<UserMovieLike> findByUserIdAndMovieId(String userId, String movieId) {
        return userMovieLikeJpaRepository.findByUserIdAndMovieId(userId, movieId)
                .map(UserMovieLikeEntity::toDomain);
    }
}
