package coz.springboot.domain.menu;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.enums.menu.MenuType;

@Getter
@Setter
@NoArgsConstructor
public class MenuTree {

    private String menuId;

    private String menuName;

    private MenuType menuType;

    private String parentMenuId;

    private Integer sortOrder;

    private List<MenuTree> subMenus;
}
