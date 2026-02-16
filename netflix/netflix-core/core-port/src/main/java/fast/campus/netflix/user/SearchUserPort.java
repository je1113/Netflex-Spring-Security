package fast.campus.netflix.user;

import fast.campus.netflix.auth.NetflixUser;

import java.util.Optional;

public interface SearchUserPort {
    Optional<NetflixUser> findByEmail(String email);
    NetflixUser getByEmail(String email);
    Optional<NetflixUser> findByProviderId(String providerId);
}
