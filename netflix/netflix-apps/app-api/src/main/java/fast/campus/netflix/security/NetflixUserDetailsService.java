package fast.campus.netflix.security;

import fast.campus.netflix.user.FetchUserUseCase;
import fast.campus.netflix.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NetflixUserDetailsService implements UserDetailsService {

    private final FetchUserUseCase fetchUserUseCase;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserResponse userResponse = fetchUserUseCase.findUserByEmail(email);
        return new NetflixAuthUser(
                userResponse.userId(),
                userResponse.username(),
                userResponse.password(),
                userResponse.email(),
                userResponse.phone(),
                List.of(new SimpleGrantedAuthority(userResponse.role()))
        );

    }
}
