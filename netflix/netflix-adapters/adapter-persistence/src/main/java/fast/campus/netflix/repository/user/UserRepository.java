package fast.campus.netflix.repository.user;

import fast.campus.netflix.auth.NetflixUser;
import fast.campus.netflix.entity.user.SocialUserEntity;
import fast.campus.netflix.entity.user.UserEntity;
import fast.campus.netflix.exception.UserException;
import fast.campus.netflix.repository.subscription.UserSubscriptionRepository;
import fast.campus.netflix.subscription.UserSubscription;
import fast.campus.netflix.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository implements SearchUserPort, InsertUserPort {

    private final UserJpaRepository userJpaRepository;
    private final SocialUserJpaRepository socialUserJpaRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<NetflixUser> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(UserEntity::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public NetflixUser getByEmail(String email) {
        Optional<NetflixUser> byEmail = findByEmail(email);
        if (byEmail.isEmpty()) {
            throw new UserException.UserDoesNotExistException();
        }

        return byEmail.get();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NetflixUser> findByProviderId(String providerId) {
        Optional<SocialUserEntity> userEntityOptional = socialUserJpaRepository.findByProvider(providerId);
        if (userEntityOptional.isEmpty()) {
            return Optional.empty();
        }

        SocialUserEntity socialUserEntity = userEntityOptional.get();

        Optional<UserSubscription> byUserId = userSubscriptionRepository.findByUserId(socialUserEntity.getSocialUserId());

        return Optional.of(new NetflixUser(
                socialUserEntity.getSocialUserId(),
                socialUserEntity.getUsername(),
                null,
                null,
                null,
                socialUserEntity.getProvider(),
                socialUserEntity.getProviderId(),
                byUserId.orElse(UserSubscription.newSubscription(socialUserEntity.getSocialUserId()))
                        .getSubscriptionType()
                        .toRole()
        ));
    }

    @Override
    @Transactional
    public NetflixUser create(CreateUser create) {
        UserEntity user = UserEntity.toEntity(create);
        userSubscriptionRepository.create(user.getUserId());
        return userJpaRepository.save(user)
                .toDomain();
    }

    @Override
    public NetflixUser createSocialUser(String username, String provider, String providerId) {
        SocialUserEntity socialUserEntity = new SocialUserEntity(username, provider, providerId);
        userSubscriptionRepository.create(socialUserEntity.getSocialUserId());
        return socialUserJpaRepository.save(socialUserEntity)
                .toDomain();
    }
}
