package com.coztion.user;

import java.util.List;

import com.coztion.user.model.UserAuthoritiesSearch;
import com.coztion.user.model.UserAuthority;
import com.coztion.user.model.UserAuthoritySave;
import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAuthorityRepository {

    Page<UserAuthority> selectUserAuthorities(UserAuthoritiesSearch userAuthoritiesSearch);

    int insertUserAuthorities(List<UserAuthoritySave> userAuthoritySaves);

    int deleteUserAuthoritiesByUsername(String username);
}
