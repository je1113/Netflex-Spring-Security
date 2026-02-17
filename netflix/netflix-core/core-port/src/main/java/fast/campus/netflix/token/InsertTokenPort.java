package fast.campus.netflix.token;

import fast.campus.netflix.auth.NetflixToken;

public interface InsertTokenPort {
    NetflixToken create(String userId, String accessToken, String refreshToken);
}
