package fast.campus.netflix.user;

import fast.campus.netflix.auth.NetflixUser;

public interface InsertUserPort {
    NetflixUser create(CreateUser user);
}
