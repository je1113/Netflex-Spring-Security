package fast.campus.netflix.controller.user.request;

import lombok.Getter;

@Getter
public class UserRegisterRequest {
    private final String username;
    private final String password;
    private final String email;
    private final String phone;

    public UserRegisterRequest(String username, String password, String email, String phone){
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }
}
