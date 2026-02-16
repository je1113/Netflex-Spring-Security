package fast.campus.netflix.user.response;

import lombok.Builder;

@Builder
public record SimpleUserResponse(
        String username,
        String email,
        String phone
) {
}
