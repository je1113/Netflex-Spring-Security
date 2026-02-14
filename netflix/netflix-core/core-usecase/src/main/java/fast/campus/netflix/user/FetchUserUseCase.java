package fast.campus.netflix.user;

import fast.campus.netflix.user.command.UserResponse;

public interface FetchUserUseCase {
    UserResponse findUserByEmail(String email);
}
