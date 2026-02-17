package fast.campus.netflix.user;

import fast.campus.netflix.auth.NetflixUser;
import fast.campus.netflix.exception.UserException;
import fast.campus.netflix.user.command.SocialUserRegistrationCommand;
import fast.campus.netflix.user.command.UserRegistrationCommand;
import fast.campus.netflix.user.response.DetailUserResponse;
import fast.campus.netflix.user.response.SocialUserResponse;
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
    private final KakaoUserPort kakaoUserPort;

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
    public DetailUserResponse findDetailUserByEmail(String email) {
        Optional<NetflixUser> byEmail = searchUserPort.findByEmail(email);
        if(byEmail.isEmpty()){
            throw new UserException.UserDoesNotExistException();
        }
        NetflixUser netflixUser = byEmail.get();
        return DetailUserResponse.builder()
                .userId(netflixUser.getUserId())
                .username(netflixUser.getUsername())
                .email(netflixUser.getEmail())
                .password(netflixUser.getEncryptedPassword())
                .phone(netflixUser.getPhone())
                .build();
    }

    @Override
    public UserResponse findByProviderId(String providerId) {
        return searchUserPort.findByProviderId(providerId)
                .map(UserResponse::toUserResponse).orElse(null);
    }

    @Override
    public SocialUserResponse findKakaoUser(String accessToken) {
        NetflixUser userFromKakao = kakaoUserPort.findUserFromKakao(accessToken);
        return new SocialUserResponse(
                userFromKakao.getUsername(), "kakao", userFromKakao.getProviderId()
        );
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

    @Override
    public UserRegistrationResponse registerSocialUser(SocialUserRegistrationCommand request) {
        Optional<NetflixUser> byProvider = searchUserPort.findByProviderId(request.providerId());
        if(byProvider.isPresent()){
            return null;
        }
        NetflixUser socialUser = insertUserPort.createSocialUser(request.username(), request.provider(), request.providerId());
        return new UserRegistrationResponse(socialUser.getUsername(), null, null);
    }
}
