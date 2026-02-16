package fast.campus.netflix.auth;

public interface KakaoTokenPort {
    String getAccessTokenByCode(String code);
}
