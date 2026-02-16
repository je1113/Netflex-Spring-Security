package fast.campus.netflix.auth;

import fast.campus.netflix.response.TokenResponse;

public interface CreateTokenUseCase {
    TokenResponse createNewToken(String userId);
}
