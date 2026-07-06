package coz.springboot.user;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.user.domain.User;
import coz.springboot.user.domain.UserSearch;

@Mapper
public interface UserRepository {

    Optional<User> selectUser(UserSearch userSearch);
}
