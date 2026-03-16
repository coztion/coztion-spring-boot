package coz.springboot.repository.menu;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.domain.menu.Menu;
import coz.springboot.domain.menu.MenuFilter;
import coz.springboot.domain.menu.MenuTree;
import coz.springboot.enums.user.Authority;

@Mapper
public interface MenuRepository {

    List<Menu> findMenusByFilter(MenuFilter filter);

    List<MenuTree> findMenuTree(List<Authority> authorities);
}
