package coz.springboot.service.menu;

import java.util.List;

import coz.springboot.enums.user.Authority;
import coz.springboot.vo.menu.Menu;
import coz.springboot.vo.menu.MenuFilter;
import coz.springboot.vo.menu.MenuTree;

public interface MenuService {

    List<Menu> getMenus(MenuFilter filter);

    List<MenuTree> getMenuTree(List<Authority> authorities);
}
