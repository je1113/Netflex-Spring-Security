package fast.campus.netflix.movie;

import lombok.Getter;

@Getter
public class TmdbMovie {
    private final String movieName;
    private final Boolean isAdult;
    private final String genre;
    private final String overview;
    private final String releaseAt;

    public TmdbMovie(String movieName, Boolean isAdult, String genre, String overview, String releaseAt) {
        this.movieName = movieName;
        this.isAdult = isAdult;
        this.genre = genre;
        this.overview = overview;
        this.releaseAt = releaseAt;
    }
}
