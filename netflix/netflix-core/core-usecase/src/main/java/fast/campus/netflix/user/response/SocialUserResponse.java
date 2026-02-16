package fast.campus.netflix.user.response;

public record SocialUserResponse(
        String name,
        String provider,
        String providerId
) {
}
