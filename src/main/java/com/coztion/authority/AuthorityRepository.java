package com.coztion.authority;

import com.coztion.authority.model.Authority;
import com.coztion.authority.model.AuthoritiesSearch;
import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthorityRepository {

    Page<Authority> selectAuthorities(AuthoritiesSearch authoritiesSearch);
}
