package fast.campus.netflix.controller.user;

import fast.campus.netflix.controller.NetflixApiResponse;
import fast.campus.netflix.controller.user.request.UserRegisterRequest;
import fast.campus.netflix.user.FetchUserUseCase;
import fast.campus.netflix.user.RegisterUserUseCase;
import fast.campus.netflix.user.command.UserRegistrationCommand;
import fast.campus.netflix.user.response.UserRegistrationResponse;
import fast.campus.netflix.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final FetchUserUseCase fetchUserUseCase;

    @GetMapping("/{email}")
    public NetflixApiResponse<UserResponse> findUserByEmail(
            @PathVariable String email
    ) {
        return NetflixApiResponse.ok(fetchUserUseCase.findUserByEmail(email));
    }

    @PostMapping("/register")
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
