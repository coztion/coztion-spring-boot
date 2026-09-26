package com.coztion.menu;

import java.util.List;
import java.util.Optional;

import com.coztion.menu.model.Menu;
import com.coztion.menu.model.MenuDelete;
import com.coztion.menu.model.MenuSave;
import com.coztion.menu.model.MenuSearch;
import com.coztion.menu.model.MenuTreeNode;
import com.coztion.menu.model.MenuUpdate;
import com.coztion.menu.model.MenusSearch;
import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuRepository {

    Page<Menu> selectMenus(MenusSearch menusSearch);

    Optional<Menu> selectMenu(MenuSearch menuSearch);

    List<MenuTreeNode> selectMenuTreeNodes(List<String> permissionCodes);

    int insertMenu(MenuSave menuSave);

    int updateMenu(MenuUpdate menuUpdate);

    int deleteMenu(MenuDelete menuDelete);
}
