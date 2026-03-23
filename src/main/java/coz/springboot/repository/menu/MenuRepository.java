package coz.springboot.repository.menu;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.enums.user.Authority;
import coz.springboot.vo.menu.Menu;
import coz.springboot.vo.menu.MenuFilter;
import coz.springboot.vo.menu.MenuTree;

@Mapper
public interface MenuRepository {

    List<Menu> findMenusByFilter(MenuFilter filter);

    List<MenuTree> findMenuTree(List<Authority> authorities);
}
