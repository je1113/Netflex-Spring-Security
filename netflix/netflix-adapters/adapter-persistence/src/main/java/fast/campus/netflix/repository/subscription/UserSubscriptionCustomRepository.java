package fast.campus.netflix.repository.subscription;

import fast.campus.netflix.subscription.UserSubscription;

import java.util.Optional;

public interface UserSubscriptionCustomRepository {
    Optional<UserSubscription> findByUserId(String userId);
}
