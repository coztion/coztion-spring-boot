package com.coztion.menu;

import java.util.List;

import com.coztion.menu.model.MenuAuthoritiesSearch;
import com.coztion.menu.model.MenuAuthority;
import com.coztion.menu.model.MenuAuthoritySave;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuAuthorityRepository {

    List<MenuAuthority> selectMenuAuthorities(MenuAuthoritiesSearch menuAuthoritiesSearch);

    int insertMenuAuthorities(List<MenuAuthoritySave> menuAuthoritySaves);
}
