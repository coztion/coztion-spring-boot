package coz.springboot.domain.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.domain.common.DataRecord;

@Getter
@Setter
@NoArgsConstructor
public class Menu extends DataRecord {

    private String menuId;

    private String menuName;

    private String menuType;

    private String parentMenuId;

    private Integer sortOrder;

    private Boolean isUse;
}
