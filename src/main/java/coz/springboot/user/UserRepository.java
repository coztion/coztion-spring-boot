package coz.springboot.user;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.user.model.User;
import coz.springboot.user.model.UserFilter;

@Mapper
public interface UserRepository {

    List<User> findUsersByFilter(UserFilter filter);

    Optional<User> findUserByFilter(UserFilter filter);
}
