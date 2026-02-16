package fast.campus.netflix.user.response;

import lombok.Builder;

@Builder
public record DetailUserResponse(
        String userId,
        String username,
        String password,
        String email,
        String phone
) {
}
