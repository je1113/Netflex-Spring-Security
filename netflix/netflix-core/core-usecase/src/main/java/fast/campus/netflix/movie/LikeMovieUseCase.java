package fast.campus.netflix.movie;

public interface LikeMovieUseCase {
    Boolean like(String userId, String movieId);
    Boolean unlike(String userId, String movieId);
}
