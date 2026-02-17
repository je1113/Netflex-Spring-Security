package fast.campus.netflix.controller.auth;

import fast.campus.netflix.auth.FetchTokenUseCase;
import fast.campus.netflix.auth.UpdateTokenUseCase;
import fast.campus.netflix.controller.NetflixApiResponse;
import fast.campus.netflix.controller.auth.request.LoginRequest;
import fast.campus.netflix.exception.ErrorCode;
import fast.campus.netflix.response.TokenResponse;
import fast.campus.netflix.security.NetflixAuthUser;
import fast.campus.netflix.user.FetchUserUseCase;
import fast.campus.netflix.user.RegisterUserUseCase;
import fast.campus.netflix.user.command.SocialUserRegistrationCommand;
import fast.campus.netflix.user.response.SocialUserResponse;
import fast.campus.netflix.user.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UpdateTokenUseCase updateTokenUseCase;
    private final FetchTokenUseCase fetchTokenUseCase;
    private final FetchUserUseCase fetchUserUseCase;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RegisterUserUseCase registerUserUseCase;

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
    @PostMapping("/reissue")
    public NetflixApiResponse<TokenResponse> reissue(HttpServletRequest httpServletRequest) {
        String refreshToken = httpServletRequest.getHeader("refresh_token");
        String accessToken = httpServletRequest.getHeader("token");
        if (StringUtils.isBlank(refreshToken) || StringUtils.isBlank(accessToken)) {
            return NetflixApiResponse.fail(ErrorCode.DEFAULT_ERROR, "토큰이 없습니다.");
        }

        return NetflixApiResponse.ok(updateTokenUseCase.reissueToken(accessToken, refreshToken));
    }

    @PostMapping("/callback")
    public NetflixApiResponse<TokenResponse> kakaoCallback(@RequestBody Map<String, String> request) {
        String code = request.get("code");

        String tokenFromKakao = fetchTokenUseCase.getTokenFromKakao(code);
        SocialUserResponse kakaoUser = fetchUserUseCase.findKakaoUser(tokenFromKakao);

        UserResponse byProviderId = fetchUserUseCase.findByProviderId(kakaoUser.providerId());
        if (ObjectUtils.isEmpty(byProviderId)) {
            registerUserUseCase.registerSocialUser(new SocialUserRegistrationCommand(
                    kakaoUser.name(),
                    kakaoUser.provider(),
                    kakaoUser.providerId()
            ));
        }
        return NetflixApiResponse.ok(updateTokenUseCase.upsertToken(kakaoUser.providerId()));
    }
}
