package com.coztion.menu.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuInfo {

    @Schema(description = "Menu ID")
    private String menuId;

    @Schema(description = "Menu Name")
    private String menuName;

    @Schema(description = "Menu Type")
    private String menuType;

    @Schema(description = "Parent Menu ID")
    private String parentMenuId;

    @Schema(description = "URL")
    private String url;

    @Schema(description = "Sort Order")
    private Integer sortOrder;

    @Schema(description = "Sub Menu List")
    private List<MenuInfo> subMenus;
}
