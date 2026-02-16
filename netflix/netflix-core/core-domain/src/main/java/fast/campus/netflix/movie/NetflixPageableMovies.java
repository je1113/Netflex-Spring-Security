package fast.campus.netflix.movie;

import lombok.Getter;

import java.util.List;

@Getter
public class NetflixPageableMovies {
    private final List<NetflixMovie> netflixMovies;

    private final int page;
    private final boolean hasNext;

    public NetflixPageableMovies(List<NetflixMovie> netflixMovies, int page, boolean hasNext) {
        this.netflixMovies = netflixMovies;
        this.page = page;
        this.hasNext = hasNext;
    }
}
