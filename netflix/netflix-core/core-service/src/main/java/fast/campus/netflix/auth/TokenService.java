package fast.campus.netflix.auth;

import fast.campus.netflix.response.TokenResponse;
import fast.campus.netflix.token.InsertTokenPort;
import fast.campus.netflix.token.SearchTokenPort;
import fast.campus.netflix.token.UpdateTokenPort;
import fast.campus.netflix.user.FetchUserUseCase;
import fast.campus.netflix.user.response.UserResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService implements UpdateTokenUseCase, CreateTokenUseCase, FetchTokenUseCase {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expire.access-token}")
    private int accessTokenExpireHour;

    @Value("${jwt.expire.refresh-token}")
    private int refreshTokenExpireHour;

    private final SearchTokenPort searchTokenPort;
    private final InsertTokenPort insertTokenPort;
    private final UpdateTokenPort updateTokenPort;
    private final FetchUserUseCase fetchUserUseCase;
    private final KakaoTokenPort kakaoTokenPort;


    @Override
    public TokenResponse updateNewToken(String userId) {

        String accessToken = getToken(userId, Duration.ofHours(accessTokenExpireHour));
        String refreshToken = getToken(userId, Duration.ofHours(refreshTokenExpireHour));
        updateTokenPort.updateToken(userId, accessToken, refreshToken);
        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public TokenResponse upsertToken(String userId) {
        return searchTokenPort.findByUserId(userId)
                .map(token -> updateNewToken(userId))
                .orElseGet(() -> createNewToken(userId));
    }

    @Override
    public TokenResponse reissueToken(String accessToken, String refreshToken) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(accessToken);

        String userId = (String) claimsJws.getPayload().get("userId");
        Optional<NetflixToken> byUserId = searchTokenPort.findByUserId(userId);
        if (byUserId.isEmpty()) {
            throw new RuntimeException("토큰이 유효하지 않습니다.");
        }

        NetflixToken netflixToken = byUserId.get();
        if (!netflixToken.getRefreshToken().equals(refreshToken)) {
            throw new RuntimeException("리프레시 토큰이 유효하지 않습니다.");
        }

        return updateNewToken(userId);
    }

    @Override
    public TokenResponse createNewToken(String userId) {
        String accessToken = getToken(userId, Duration.ofHours(accessTokenExpireHour));
        String refreshToken = getToken(userId, Duration.ofHours(refreshTokenExpireHour));
        NetflixToken netflixToken = insertTokenPort.create(userId, accessToken, refreshToken);
        return TokenResponse.builder()
                .accessToken(netflixToken.getAccessToken())
                .refreshToken(netflixToken.getRefreshToken())
                .build();
    }

    @Override
    public TokenResponse findTokenByUserId(String userId) {
        Optional<NetflixToken> tokenOptional = searchTokenPort.findByUserId(userId);
        if (tokenOptional.isEmpty()) {
            throw new RuntimeException();
        }

        NetflixToken netflixToken = tokenOptional.get();
        TokenResponse build = TokenResponse.builder()
                .accessToken(netflixToken.getAccessToken())
                .refreshToken(netflixToken.getRefreshToken())
                .build();
        return build;
    }

    @Override
    public UserResponse findUserByAccessToken(String accessToken) {
        Claims claims = parseClaims(accessToken);
        Object userId = claims.get("userId");
        if(ObjectUtils.isEmpty(userId)){
            throw new RuntimeException("권한 정보가 없는 토큰입니다");
        }
        return fetchUserUseCase.findByProviderId(userId.toString());
    }

    @Override
    public Boolean validateToken(String accessToken) {
        Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(accessToken);

        return true;
    }

    @Override
    public String getTokenFromKakao(String code) {
        return kakaoTokenPort.getAccessTokenByCode(code);
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String getToken(String userId, Duration expireAt) {
        Date now = new Date();
        Instant instant = now.toInstant();

        return Jwts.builder()
                .claim("userId", userId)
                .issuedAt(now)
                .expiration(Date.from(instant.plus(expireAt)))
                .signWith(getSigningKey())
                .compact();
    }

    private Claims parseClaims(String accessToken){
        try{
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }
}
