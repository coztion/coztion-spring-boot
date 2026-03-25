package coz.springboot.menu.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.global.model.DataRecord;

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
