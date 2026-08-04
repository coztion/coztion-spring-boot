package com.coztion.user;

import java.util.Optional;

import com.coztion.user.model.User;
import com.coztion.user.model.UserSearch;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {

    Optional<User> selectUser(UserSearch userSearch);
}
