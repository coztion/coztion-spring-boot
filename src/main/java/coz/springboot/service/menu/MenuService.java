package coz.springboot.service.menu;

import java.util.List;

import coz.springboot.domain.menu.Menu;
import coz.springboot.domain.menu.MenuFilter;

public interface MenuService {

    List<Menu> getMenus(MenuFilter filter);
}
