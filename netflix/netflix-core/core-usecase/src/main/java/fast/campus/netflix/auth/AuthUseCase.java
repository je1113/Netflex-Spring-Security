package fast.campus.netflix.auth;

import fast.campus.netflix.response.TokenResponse;

public interface AuthUseCase {
    TokenResponse login(String email, String password);
}
