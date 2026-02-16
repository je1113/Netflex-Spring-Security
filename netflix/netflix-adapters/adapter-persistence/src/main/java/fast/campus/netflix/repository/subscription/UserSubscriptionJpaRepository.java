package fast.campus.netflix.repository.subscription;

import fast.campus.netflix.entity.subscription.UserSubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSubscriptionJpaRepository extends JpaRepository<UserSubscriptionEntity, String>, UserSubscriptionCustomRepository {
}
