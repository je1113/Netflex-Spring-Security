package fast.campus.netflix.user;

import fast.campus.netflix.user.response.DetailUserResponse;
import fast.campus.netflix.user.response.SocialUserResponse;
import fast.campus.netflix.user.response.UserResponse;

public interface FetchUserUseCase {
    UserResponse findUserByEmail(String email);

    DetailUserResponse findDetailUserByEmail(String email);

    UserResponse findByProviderId(String providerId);

    SocialUserResponse findKakaoUser(String accessToken);
}
