package coz.springboot.dto.menu;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import coz.springboot.domain.menu.Menu;
import coz.springboot.dto.common.DataRecordResponse;

@Getter
@SuperBuilder
public class MenuGetResponse extends DataRecordResponse {

    @Schema(description = "메뉴ID")
    private String menuId;

    @Schema(description = "메뉴명")
    private String menuName;

    @Schema(description = "메뉴타입")
    private String menuType;

    @Schema(description = "상위메뉴ID")
    private String parentMenuId;

    @Schema(description = "정렬순서")
    private Integer sortOrder;

    @Schema(description = "사용여부")
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
