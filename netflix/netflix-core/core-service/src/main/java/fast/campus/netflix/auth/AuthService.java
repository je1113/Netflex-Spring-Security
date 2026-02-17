package fast.campus.netflix.auth;

import fast.campus.netflix.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthUseCase{
    @Override
    public TokenResponse login(String email, String password) {
        return null;
    }
}
