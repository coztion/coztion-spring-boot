package coz.springboot.menu;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.menu.domain.Menu;
import coz.springboot.menu.domain.MenuFilter;
import coz.springboot.menu.domain.MenuTree;
import coz.springboot.user.domain.Authority;

@Mapper
public interface MenuRepository {

    List<Menu> findMenusByFilter(MenuFilter filter);

    List<MenuTree> findMenuTree(List<Authority> authorities);
}
