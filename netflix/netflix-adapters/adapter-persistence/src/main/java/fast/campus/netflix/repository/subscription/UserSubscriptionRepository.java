package fast.campus.netflix.repository.subscription;

import fast.campus.netflix.entity.subscription.UserSubscriptionEntity;
import fast.campus.netflix.subscription.FetchUserSubscriptionPort;
import fast.campus.netflix.subscription.InsertUserSubscriptionPort;
import fast.campus.netflix.subscription.UpdateUserSubscriptionPort;
import fast.campus.netflix.subscription.UserSubscription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserSubscriptionRepository implements UpdateUserSubscriptionPort, InsertUserSubscriptionPort, FetchUserSubscriptionPort {

    private final UserSubscriptionJpaRepository userSubscriptionJpaRepository;

    @Override
    public UserSubscription create(String userId) {
        UserSubscription userSubscription = UserSubscription.newSubscription(userId);
        UserSubscriptionEntity entity = UserSubscriptionEntity.toEntity(userSubscription);
        return userSubscriptionJpaRepository.save(entity)
                .toDomain();
    }

    @Override
    public UserSubscription update(UserSubscription userSubscription) {
        return userSubscriptionJpaRepository.save(UserSubscriptionEntity.toEntity(userSubscription))
                .toDomain();
    }

    @Override
    public Optional<UserSubscription> findByUserId(String userId) {
        return userSubscriptionJpaRepository.findByUserId(userId);
    }
}
