package coz.springboot.menu.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import coz.springboot.global.dto.response.DataRecordResponse;
import coz.springboot.menu.domain.Menu;

@Getter
@SuperBuilder
public class MenuGetResponse extends DataRecordResponse {

    @Schema(description = "Menu ID")
    private String menuId;

    @Schema(description = "Menu Name")
    private String menuName;

    @Schema(description = "Menu Type")
    private String menuType;

    @Schema(description = "Parent Menu ID")
    private String parentMenuId;

    @Schema(description = "Sort Order")
    private Integer sortOrder;

    @Schema(description = "In Use")
    private Boolean isUse;

    public static MenuGetResponse ofDomain(Menu menu) {
        return MenuGetResponse.builder()
                .menuId(menu.getMenuId())
                .menuName(menu.getMenuName())
                .menuType(menu.getMenuType().getValue())
                .parentMenuId(menu.getParentMenuId())
                .sortOrder(menu.getSortOrder())
                .isUse(menu.getIsUse())
                .dataRecord(menu)
                .build();
    }
}
