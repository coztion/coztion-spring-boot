package coz.springboot.core.user.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.core.user.dto.UserSearchParam;
import coz.springboot.core.user.vo.User;

@Mapper
public interface UserRepository {

    List<User> findUsersByParam(UserSearchParam param);

    Optional<User> findUserByParam(UserSearchParam param);
}
