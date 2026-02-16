package fast.campus.netflix.user.command;

import lombok.Builder;

@Builder
public record SocialUserRegistrationCommand(String username, String provider, String providerId) {
}
