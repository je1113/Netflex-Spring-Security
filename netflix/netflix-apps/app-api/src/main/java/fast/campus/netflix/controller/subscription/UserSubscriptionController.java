package fast.campus.netflix.controller.subscription;

import fast.campus.netflix.authentication.token.JwtTokenProvider;
import fast.campus.netflix.controller.NetflixApiResponse;
import fast.campus.netflix.subscription.SubscriptionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscription")
@RequiredArgsConstructor
public class UserSubscriptionController {

    private final JwtTokenProvider jwtTokenProvider;
    private final SubscriptionUseCase subscriptionUseCase;

    // 구독 취소
    @PostMapping("/unsubscribe")
    @PreAuthorize("hasAnyRole('ROLE_BRONZE', 'ROLE_SILVER', 'ROLE_GOLD')")
    public NetflixApiResponse<Boolean> unsubscribe() {
        String userId = jwtTokenProvider.getUserId();
        subscriptionUseCase.unsubscribe(userId);
        return NetflixApiResponse.ok(true);
    }

    // 동일한 구독권으로 갱신
    @PostMapping("/renew")
    @PreAuthorize("hasAnyRole('ROLE_BRONZE', 'ROLE_SILVER', 'ROLE_GOLD')")
    public NetflixApiResponse<Boolean> renew() {
        String userId = jwtTokenProvider.getUserId();
        subscriptionUseCase.renewSubscription(userId);
        return NetflixApiResponse.ok(true);
    }

    // 구독권 변경
    @PatchMapping("/{type}")
    @PreAuthorize("hasAnyRole('ROLE_FREE', 'ROLE_BRONZE', 'ROLE_SILVER', 'ROLE_GOLD')")
    public NetflixApiResponse<Boolean> change(@PathVariable String type) {
        String userId = jwtTokenProvider.getUserId();
        subscriptionUseCase.changeSubscription(userId, type);
        return NetflixApiResponse.ok(true);
    }
}
