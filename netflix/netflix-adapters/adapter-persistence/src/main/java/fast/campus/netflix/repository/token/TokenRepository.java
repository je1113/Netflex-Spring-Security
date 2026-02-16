package fast.campus.netflix.repository.token;

import fast.campus.netflix.auth.NetplixToken;
import fast.campus.netflix.entity.token.TokenEntity;
import fast.campus.netflix.token.InsertTokenPort;
import fast.campus.netflix.token.SearchTokenPort;
import fast.campus.netflix.token.UpdateTokenPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TokenRepository implements InsertTokenPort, UpdateTokenPort, SearchTokenPort {

    private final TokenJpaRepository tokenJpaRepository;

    @Override
    public NetplixToken create(String userId, String accessToken, String refreshToken) {
        TokenEntity entity = TokenEntity.toEntity(userId, accessToken, refreshToken);
        return tokenJpaRepository.save(entity).toDomain();
    }

    @Override
    public Optional<NetplixToken> findByUserId(String userId) {
        return tokenJpaRepository.findByUserId(userId)
                .map(TokenEntity::toDomain);
    }

    @Override
    public void updateToken(String userId, String accessToken, String refreshToken) {
        Optional<TokenEntity> byUserId = tokenJpaRepository.findByUserId(userId);
        if (byUserId.isEmpty()) {
            throw new RuntimeException();
        }

        TokenEntity tokenEntity = byUserId.get();
        tokenEntity.updateToken(accessToken, refreshToken);
        tokenJpaRepository.save(tokenEntity);
    }
}
