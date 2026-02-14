package fast.campus.netflix.controller.user;

import fast.campus.netflix.controller.NetflixApiResponse;
import fast.campus.netflix.controller.user.request.UserRegisterRequest;
import fast.campus.netflix.user.RegisterUserUseCase;
import fast.campus.netflix.user.command.UserRegistrationCommand;
import fast.campus.netflix.user.response.UserRegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final RegisterUserUseCase registerUserUseCase;
    @PostMapping("/api/v1/user/register")
    public NetflixApiResponse<UserRegistrationResponse> register(@RequestBody UserRegisterRequest request){
        UserRegistrationResponse register =  registerUserUseCase.register(
                UserRegistrationCommand.builder()
                        .email(request.getEmail())
                        .phone(request.getPhone())
                        .username(request.getUsername())
                        .encryptedPassword(request.getPassword())
                        .build()
        );
        return NetflixApiResponse.ok(register);
    }
}
