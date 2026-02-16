package coz.springboot.service.user;

import java.util.List;

import coz.springboot.dto.user.UserSearchParam;
import coz.springboot.vo.user.User;

public interface UserService {

    List<User> getUsers(UserSearchParam command);

    User getUser(UserSearchParam command);
}
