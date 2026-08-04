package com.coztion.menu;

import java.util.List;

import com.coztion.menu.model.Menu;
import com.coztion.menu.model.MenuInfo;
import com.coztion.menu.model.MenuUpdate;
import com.coztion.menu.model.MenusSearch;
import com.coztion.user.model.Authority;
import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuRepository {

    Page<Menu> selectMenus(MenusSearch menusSearch);

    List<MenuInfo> selectMenuInfos(List<Authority> authorities);

    int updateMenu(MenuUpdate update);
}
