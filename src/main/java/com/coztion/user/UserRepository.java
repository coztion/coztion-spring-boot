package com.coztion.user;

import java.util.Optional;

import com.coztion.user.model.User;
import com.coztion.user.model.UserDelete;
import com.coztion.user.model.UserSave;
import com.coztion.user.model.UserSearch;
import com.coztion.user.model.UserUpdate;
import com.coztion.user.model.UsersSearch;
import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {

    Optional<User> selectUser(UserSearch userSearch);

    Page<User> selectUsers(UsersSearch usersSearch);

    int insertUser(UserSave userSave);

    int updateUser(UserUpdate userUpdate);

    int deleteUser(UserDelete userDelete);
}
