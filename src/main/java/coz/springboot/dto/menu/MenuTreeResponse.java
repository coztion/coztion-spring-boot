package coz.springboot.dto.menu;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Getter;

import coz.springboot.domain.menu.MenuTree;

@Getter
@Builder
public class MenuTreeResponse {

    @Schema(description = "Menu ID")
    private String menuId;

    @Schema(description = "Menu Name")
    private String menuName;

    @Schema(description = "Menu Type")
    private String menuType;

    @Schema(description = "Sort Order")
    private Integer sortOrder;

    @Schema(description = "Sub Menu List")
    private List<MenuTreeResponse> subMenus;

    public static MenuTreeResponse ofDomain(MenuTree menu) {
        return MenuTreeResponse.builder()
                .menuId(menu.getMenuId())
                .menuName(menu.getMenuName())
                .menuType(menu.getMenuType().getValue())
                .sortOrder(menu.getSortOrder())
                .subMenus(
                        menu.getSubMenus() == null
                                ? null
                                : menu.getSubMenus().stream()
                                        .map(MenuTreeResponse::ofDomain)
                                        .toList())
                .build();
    }
}
