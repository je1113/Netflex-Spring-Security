package fast.campus.netflix.movie;

import java.util.Optional;

public interface LikeMoviePort {
    UserMovieLike save(UserMovieLike domain);

    Optional<UserMovieLike> findByUserIdAndMovieId(String userId, String movieId);
}
