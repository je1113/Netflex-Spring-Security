package fast.campus.netflix.auth;

import fast.campus.netflix.response.TokenResponse;
import fast.campus.netflix.user.response.UserResponse;

public interface FetchTokenUseCase {
    TokenResponse findTokenByUserId(String userId);

    UserResponse findUserByAccessToken(String accessToken);

    Boolean validateToken(String accessToken);

    String getTokenFromKakao(String code);
}
