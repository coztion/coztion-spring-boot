package coz.springboot.menu;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.menu.model.Menu;
import coz.springboot.menu.model.MenuFilter;
import coz.springboot.menu.model.MenuTree;
import coz.springboot.user.model.Authority;

@Mapper
public interface MenuRepository {

    List<Menu> findMenusByFilter(MenuFilter filter);

    List<MenuTree> findMenuTree(List<Authority> authorities);
}
