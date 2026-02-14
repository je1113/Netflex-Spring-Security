package fast.campus.netflix.movie.response;

import lombok.Getter;

@Getter
public class MovieResponse {
    private final String movieName;
    private final Boolean isAdult;
    private final String genre;
    private final String overview;
    private final String releaseAt;

    public MovieResponse(String movieName, Boolean isAdult, String genre, String overview, String releaseAt) {
        this.movieName = movieName;
        this.isAdult = isAdult;
        this.genre = genre;
        this.overview = overview;
        this.releaseAt = releaseAt;
    }
}
