package fast.campus.netflix.repository.user;

import fast.campus.netflix.entity.user.SocialUserEntity;

import java.util.Optional;

public interface SocialUserCustomRepository {
    Optional<SocialUserEntity> findByProvider(String providerId);
}

