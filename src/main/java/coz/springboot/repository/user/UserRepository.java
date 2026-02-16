package coz.springboot.repository.user;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.dto.user.UserSearchParam;
import coz.springboot.vo.user.User;

@Mapper
public interface UserRepository {

    List<User> findUsersByParam(UserSearchParam param);

    Optional<User> findUserByParam(UserSearchParam param);
}
