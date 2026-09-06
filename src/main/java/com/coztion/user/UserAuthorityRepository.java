package com.coztion.user;

import java.util.List;

import com.coztion.user.model.UserAuthoritiesSearch;
import com.coztion.user.model.UserAuthority;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAuthorityRepository {

    List<UserAuthority> selectUserAuthorities(UserAuthoritiesSearch userAuthoritiesSearch);
}
