package fast.campus.netflix.user.response;

import fast.campus.netflix.auth.NetflixUser;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Builder
public record UserResponse(
        String userId,
        String username,
        String password,
        String email,
        String phone,
        String provider,
        String providerId,
        String role
) {
    public static UserResponse toUserResponse(NetflixUser netplixUser) {
        return new UserResponse(
                netplixUser.getUserId(),
                netplixUser.getUsername(),
                StringUtils.defaultIfBlank(netplixUser.getEncryptedPassword(), "password"),
                StringUtils.defaultIfBlank(netplixUser.getEmail(), "email@email.com"),
                StringUtils.defaultIfBlank(netplixUser.getPhone(), "010-0000-0000"),
                StringUtils.defaultIfBlank(netplixUser.getProvider(), "no-provider"),
                StringUtils.defaultIfBlank(netplixUser.getProviderId(), "no-provider-id"),
                netplixUser.getRole()
        );
    }
}
