package fast.campus.netflix.auth;

import fast.campus.netflix.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService implements UpdateTokenUseCase {
    @Override
    public TokenResponse updateNewToken(String userId) {
        return null;
    }

    @Override
    public TokenResponse upsertToken(String userId) {
        return null;
    }

    @Override
    public TokenResponse reissueToken(String accessToken, String refreshToken) {
        return null;
    }
}
