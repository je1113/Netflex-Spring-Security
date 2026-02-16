package fast.campus.netflix.user;

import fast.campus.netflix.auth.NetflixUser;
import fast.campus.netflix.exception.UserException;
import fast.campus.netflix.user.command.UserRegistrationCommand;
import fast.campus.netflix.user.response.UserRegistrationResponse;
import fast.campus.netflix.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements FetchUserUseCase, RegisterUserUseCase{

    private final SearchUserPort searchUserPort;
    private final InsertUserPort insertUserPort;

    @Override
    public UserResponse findUserByEmail(String email) {
        Optional<NetflixUser> byEmail = searchUserPort.findByEmail(email);
        if(byEmail.isEmpty()){
            throw new UserException.UserDoesNotExistException();
        }

        NetflixUser userPortResponse = byEmail.get();
        return UserResponse.builder()
                .userId(userPortResponse.getUserId())
                .email(userPortResponse.getEmail())
                .password(userPortResponse.getEncryptedPassword())
                .phone(userPortResponse.getPhone())
                .role(userPortResponse.getRole())
                .username(userPortResponse.getUsername())
                .build();
    }

    @Override
    public UserRegistrationResponse register(UserRegistrationCommand request) {
        Optional<NetflixUser> byEmail = searchUserPort.findByEmail(request.email());
        if (byEmail.isPresent()) {
            throw new UserException.UserAlreadyExistException();
        }
        NetflixUser netflixUser = insertUserPort.create(
                CreateUser.builder()
                        .username(request.username())
                        .encryptedPassword(request.encryptedPassword())
                        .email(request.email())
                        .phone(request.phone())
                        .build()
        );
        return new UserRegistrationResponse(netflixUser.getUsername(),
                netflixUser.getEmail(), netflixUser.getPhone());

    }
}
