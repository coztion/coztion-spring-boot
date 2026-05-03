package coz.springboot.menu.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Getter;

import coz.springboot.menu.model.MenuTree;

@Getter
@Builder
public class MenuTreeGetResponse {

    @Schema(description = "Menu ID")
    private String menuId;

    @Schema(description = "Menu Name")
    private String menuName;

    @Schema(description = "Menu Type")
    private String menuType;

    @Schema(description = "URL")
    private String url;

    @Schema(description = "Sort Order")
    private Integer sortOrder;

    @Schema(description = "Sub Menu List")
    private List<MenuTreeGetResponse> subMenus;

    public static MenuTreeGetResponse fromMenuTree(MenuTree menu) {
        return MenuTreeGetResponse.builder()
                .menuId(menu.getMenuId())
                .menuName(menu.getMenuName())
                .menuType(menu.getMenuType().getValue())
                .url(menu.getUrl())
                .sortOrder(menu.getSortOrder())
                .subMenus(
                        menu.getSubMenus() == null
                                ? null
                                : menu.getSubMenus().stream()
                                        .map(MenuTreeGetResponse::fromMenuTree)
                                        .toList())
                .build();
    }
}
