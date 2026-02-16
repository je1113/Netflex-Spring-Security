package fast.campus.netflix.repository.user;

import fast.campus.netflix.auth.NetflixUser;
import fast.campus.netflix.entity.user.UserEntity;
import fast.campus.netflix.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository implements SearchUserPort, InsertUserPort {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<NetflixUser> findByEmail(String email) {
        Optional<UserEntity> byEmail = userJpaRepository.findByEmail(email);
        if(byEmail.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(NetflixUser.builder()
                .userId(byEmail.get().getUserId())
                .encryptedPassword(byEmail.get().getPassword())
                .username(byEmail.get().getEmail())
                .email(byEmail.get().getEmail())
                .phone(byEmail.get().getPhone())
                .build());
    }

    @Override
    public NetflixUser getByEmail(String email) {
        return null;
    }

    @Override
    public Optional<NetflixUser> findByProviderId(String providerId) {
        return Optional.empty();
    }

    @Override
    public NetflixUser create(CreateUser create) {
        UserEntity user = UserEntity.toEntity(create);
        return userJpaRepository.save(user)
                .toDomain();
    }
}
