package fast.campus.netflix.token;

import fast.campus.netflix.auth.NetflixToken;

import java.util.Optional;

public interface SearchTokenPort {
    Optional<NetflixToken> findByUserId(String userId);
}
