package coz.springboot.service.menu;

import java.util.List;

import coz.springboot.domain.menu.Menu;
import coz.springboot.domain.menu.MenuFilter;
import coz.springboot.domain.menu.MenuTree;
import coz.springboot.enums.user.Authority;

public interface MenuService {

    List<Menu> getMenus(MenuFilter filter);

    List<MenuTree> getMenuTree(List<Authority> authorities);
}
