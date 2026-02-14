package fast.campus.netflix.controller.auth;

import com.nimbusds.oauth2.sdk.TokenResponse;
import fast.campus.netflix.auth.UpdateTokenUseCase;
import fast.campus.netflix.controller.NetflixApiResponse;
import fast.campus.netflix.controller.auth.request.LoginRequest;
import fast.campus.netflix.security.NetflixAuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UpdateTokenUseCase updateTokenUseCase;

    @PostMapping("/login")
    public NetflixApiResponse<String> login(@RequestBody LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        NetflixAuthUser principal = (NetflixAuthUser) authentication.getPrincipal();

//        TokenResponse tokenResponse = updateTokenUseCase.upsertToken(principal.getEmail());

        return NetflixApiResponse.ok("access-token");
    }
}
