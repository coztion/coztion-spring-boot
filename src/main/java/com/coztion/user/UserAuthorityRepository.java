package com.coztion.user;

import com.coztion.user.model.UserAuthoritiesSearch;
import com.coztion.user.model.UserAuthority;
import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAuthorityRepository {

    Page<UserAuthority> selectUserAuthorities(UserAuthoritiesSearch userAuthoritiesSearch);
}
