package fast.campus.netflix.repository.user;

import fast.campus.netflix.entity.user.UserEntity;

import java.util.Optional;

public interface UserCustomRepository {
    Optional<UserEntity> findByEmail(String email);
}
