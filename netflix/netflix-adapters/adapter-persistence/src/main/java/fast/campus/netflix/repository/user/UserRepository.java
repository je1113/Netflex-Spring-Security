package fast.campus.netflix.repository.user;

import fast.campus.netflix.entity.user.UserEntity;
import fast.campus.netflix.user.FetchUserPort;
import fast.campus.netflix.user.UserPortResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository implements FetchUserPort {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<UserPortResponse> findByEmail(String email) {
        Optional<UserEntity> byEmail = userJpaRepository.findByEmail(email);
        if(byEmail.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(UserPortResponse.builder()
                .userId(byEmail.get().getUserId())
                .password(byEmail.get().getPassword())
                .username(byEmail.get().getEmail())
                .email(byEmail.get().getEmail())
                .phone(byEmail.get().getPhone())
                .build());
    }
}
