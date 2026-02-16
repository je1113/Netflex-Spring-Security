package fast.campus.netflix.token;

import fast.campus.netflix.auth.NetplixToken;

import java.util.Optional;

public interface SearchTokenPort {
    Optional<NetplixToken> findByUserId(String userId);
}
