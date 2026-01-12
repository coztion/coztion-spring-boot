package coz.springboot.core.user.service;

import java.util.List;

import coz.springboot.core.user.dto.UserSearchParam;
import coz.springboot.core.user.vo.User;

public interface UserService {

    List<User> getUsers(UserSearchParam command);

    User getUser(UserSearchParam command);
}
