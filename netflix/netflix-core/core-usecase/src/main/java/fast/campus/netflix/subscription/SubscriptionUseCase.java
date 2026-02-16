package fast.campus.netflix.subscription;

public interface SubscriptionUseCase {
    void unsubscribe(String userId);
    void renewSubscription(String userId);
    void changeSubscription(String userId, String type);
}
