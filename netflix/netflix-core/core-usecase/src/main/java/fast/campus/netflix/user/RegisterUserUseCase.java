package fast.campus.netflix.user;

import fast.campus.netflix.user.command.SocialUserRegistrationCommand;
import fast.campus.netflix.user.command.UserRegistrationCommand;
import fast.campus.netflix.user.response.UserRegistrationResponse;

public interface RegisterUserUseCase {
    UserRegistrationResponse register(UserRegistrationCommand command);

    UserRegistrationResponse registerSocialUser(SocialUserRegistrationCommand request);
}