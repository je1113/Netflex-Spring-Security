package fast.campus.netflix.subscription;

public interface InsertUserSubscriptionPort {
    UserSubscription create(String userId);
}
