package fast.campus.netflix.response;

import lombok.Builder;

@Builder
public record TokenResponse(String accessToken, String refreshToken) {
}
