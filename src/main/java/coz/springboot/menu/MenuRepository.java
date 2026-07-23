package coz.springboot.menu;

import java.util.List;

import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.menu.model.Menu;
import coz.springboot.menu.model.MenuInfo;
import coz.springboot.menu.model.MenuUpdate;
import coz.springboot.menu.model.MenusSearch;
import coz.springboot.user.model.Authority;

@Mapper
public interface MenuRepository {

    Page<Menu> selectMenus(MenusSearch menusSearch);

    List<MenuInfo> selectMenuInfos(List<Authority> authorities);

    int updateMenu(MenuUpdate update);
}
