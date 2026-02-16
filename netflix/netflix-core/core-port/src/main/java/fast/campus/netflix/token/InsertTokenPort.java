package fast.campus.netflix.token;

import fast.campus.netflix.auth.NetplixToken;

public interface InsertTokenPort {
    NetplixToken create(String userId, String accessToken, String refreshToken);
}
