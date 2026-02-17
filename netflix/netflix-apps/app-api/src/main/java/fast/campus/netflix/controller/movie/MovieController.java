package fast.campus.netflix.controller.movie;

import fast.campus.netflix.controller.NetflixApiResponse;
import fast.campus.netflix.movie.FetchMovieUseCase;
import fast.campus.netflix.movie.response.PageableMoviesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/movie")
@RequiredArgsConstructor
public class MovieController {
    private final FetchMovieUseCase fetchMovieUseCase;

    @PostMapping("/search")
    @PreAuthorize("hasAnyRole('ROLE_FREE', 'ROLE_BRONZE', 'ROLE_SILVER', 'ROLE_GOLD')")
    public NetflixApiResponse<PageableMoviesResponse> fetchMoviePageables(@PathVariable int page){
        PageableMoviesResponse pageableMoviesResponse = fetchMovieUseCase.fetchFromClient(page);
        return NetflixApiResponse.ok(pageableMoviesResponse);
    }
}
