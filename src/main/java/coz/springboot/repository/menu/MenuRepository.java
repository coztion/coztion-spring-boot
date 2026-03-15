package coz.springboot.repository.menu;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.domain.menu.Menu;
import coz.springboot.domain.menu.MenuFilter;

@Mapper
public interface MenuRepository {

    List<Menu> findMenusByFilter(MenuFilter filter);
}
