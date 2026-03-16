package coz.springboot.domain.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.domain.common.DataRecord;
import coz.springboot.enums.menu.MenuType;

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
