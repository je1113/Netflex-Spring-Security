package fast.campus.netflix.auth;

import fast.campus.netflix.response.TokenResponse;

public interface UpdateTokenUseCase {
    TokenResponse updateNewToken(String userId);

    TokenResponse upsertToken(String userId);

    TokenResponse reissueToken(String accessToken, String refreshToken);
}
