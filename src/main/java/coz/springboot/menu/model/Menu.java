package coz.springboot.menu.model;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.common.enums.YesOrNo;
import coz.springboot.common.model.DataRecord;

@Getter
@Setter
@NoArgsConstructor
public class Menu extends DataRecord {

    @Schema(description = "Menu ID")
    private String menuId;

    @Schema(description = "Menu Name")
    private String menuName;

    @Schema(description = "Menu Type")
    private MenuType menuType;

    @Schema(description = "Parent Menu ID")
    private String parentMenuId;

    @Schema(description = "Sort Order")
    private Integer sortOrder;

    @Schema(description = "In Use")
    private YesOrNo useYn = YesOrNo.YES;
}
