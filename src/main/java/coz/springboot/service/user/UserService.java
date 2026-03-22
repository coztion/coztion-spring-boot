package coz.springboot.service.user;

import java.util.List;
import java.util.Optional;

import coz.springboot.domain.user.User;
import coz.springboot.domain.user.UserFilter;

public interface UserService {

    List<User> getUsers(UserFilter filter);

    Optional<User> getUser(UserFilter filter);
}
