package coz.springboot.dto.menu;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Getter;

import coz.springboot.domain.menu.MenuTree;

@Getter
@Builder
public class MenuTreeResponse {

    @Schema(description = "메뉴ID")
    private String menuId;

    @Schema(description = "메뉴명")
    private String menuName;

    @Schema(description = "메뉴타입")
    private String menuType;

    @Schema(description = "정렬순서")
    private Integer sortOrder;

    @Schema(description = "하위메뉴목록")
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
