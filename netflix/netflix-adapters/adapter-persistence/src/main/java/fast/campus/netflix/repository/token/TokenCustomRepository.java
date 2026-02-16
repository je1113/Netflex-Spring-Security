package fast.campus.netflix.repository.token;

import fast.campus.netflix.entity.token.TokenEntity;

import java.util.Optional;

public interface TokenCustomRepository {
    Optional<TokenEntity> findByUserId(String userId);
}
