package coz.springboot.menu;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.menu.model.MenuAuthoritiesSearch;
import coz.springboot.menu.model.MenuAuthority;

@Mapper
public interface MenuAuthorityRepository {

    List<MenuAuthority> selectMenuAuthorities(MenuAuthoritiesSearch menuAuthoritiesSearch);
}
