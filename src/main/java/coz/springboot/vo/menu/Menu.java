package coz.springboot.vo.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.enums.menu.MenuType;
import coz.springboot.vo.common.DataRecord;

@Getter
@Setter
@NoArgsConstructor
public class Menu extends DataRecord {

    private String menuId;

    private String menuName;

    private MenuType menuType;

    private String parentMenuId;

    private Integer sortOrder;

    private Boolean isUse;
}
