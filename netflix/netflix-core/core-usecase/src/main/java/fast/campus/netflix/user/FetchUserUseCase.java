package fast.campus.netflix.user;

import fast.campus.netflix.user.response.UserResponse;

public interface FetchUserUseCase {
    UserResponse findUserByEmail(String email);
}
