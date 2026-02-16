package fast.campus.netflix.user;

import fast.campus.netflix.auth.NetflixUser;

public interface KakaoUserPort {
    NetflixUser findUserFromKakao(String accessToken);
}
