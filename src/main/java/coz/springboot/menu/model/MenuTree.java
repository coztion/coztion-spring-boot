package coz.springboot.menu.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuTree {

    private String menuId;

    private String menuName;

    private MenuType menuType;

    private String parentMenuId;

    private String url;

    private Integer sortOrder;

    private List<MenuTree> subMenus;
}
