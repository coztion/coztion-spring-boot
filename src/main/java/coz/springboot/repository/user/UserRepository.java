package coz.springboot.repository.user;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.vo.user.User;
import coz.springboot.vo.user.UserFilter;

@Mapper
public interface UserRepository {

    List<User> findUsersByFilter(UserFilter filter);

    Optional<User> findUserByFilter(UserFilter filter);
}
