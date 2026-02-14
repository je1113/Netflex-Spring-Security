package fast.campus.netflix.user.command;

import lombok.Builder;
import lombok.Getter;

@Builder
public record UserRegistrationCommand(String username, String encryptedPassword, String email, String phone) {
}
