package coz.springboot.menu;

import java.util.List;

import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.menu.domain.Menu;
import coz.springboot.menu.domain.MenuTree;
import coz.springboot.menu.domain.MenuUpdate;
import coz.springboot.menu.domain.MenusSearch;
import coz.springboot.user.domain.Authority;

@Mapper
public interface MenuRepository {

    Page<Menu> selectMenus(MenusSearch menusSearch);

    List<MenuTree> selectMenuTree(List<Authority> authorities);

    int updateMenu(MenuUpdate update);
}
