package fast.campus.netflix.subscription;

import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Builder
public class UserSubscription {
    private String userId;
    private SubscriptionType subscriptionType;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Boolean validYn;

    public UserSubscription(String userId, SubscriptionType subscriptionType, LocalDateTime startAt, LocalDateTime endAt, Boolean validYn) {
        this.userId = userId;
        this.subscriptionType = subscriptionType;
        this.startAt = startAt;
        this.endAt = getEndAt(startAt);
        this.validYn = true;
    }

    public void off() {
        this.validYn = false;
    }

    public void on(SubscriptionType subscription) {
        if (this.validYn) {
            return;
        }

        this.subscriptionType = subscription;
        this.startAt = LocalDateTime.now();
        this.endAt = getEndAt(this.endAt);
        this.validYn = true;
    }

    public void renew() {
        this.startAt = LocalDateTime.now();
        this.endAt = getEndAt(this.endAt);
        this.validYn = true;
    }

    public void change(SubscriptionType type) {
        this.subscriptionType = type;
    }

    public boolean ableToRenew() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(endAt);
    }

    public boolean ableToChange() {
        LocalDateTime now = LocalDateTime.now();
        return now.isBefore(endAt) && now.isAfter(startAt) && validYn;
    }

    public static UserSubscription newSubscription(String userId) {
        LocalDateTime now = LocalDateTime.now();
        return new UserSubscription(
                userId,
                SubscriptionType.FREE,
                now,
                now.plusMonths(1L),
                true
        );
    }

    private LocalDateTime getEndAt(LocalDateTime startAt) {
        return startAt.plus(Duration.ofDays(30));
    }
}
