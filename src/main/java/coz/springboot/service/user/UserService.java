package coz.springboot.service.user;

import java.util.List;

import coz.springboot.domain.user.User;
import coz.springboot.domain.user.UserFilter;

public interface UserService {

    List<User> getUsers(UserFilter filter);

    User getUser(UserFilter filter);
}
