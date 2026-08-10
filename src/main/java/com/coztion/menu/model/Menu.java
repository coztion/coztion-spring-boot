package com.coztion.menu.model;

import java.util.List;

import com.coztion.common.model.DataRecord;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Menu extends DataRecord {

    @Schema(description = "Menu ID")
    private MenuId menuId;

    @Schema(description = "Menu Name")
    private String menuName;

    @Schema(description = "Menu Type")
    private MenuType menuType;

    @Schema(description = "URL")
    private String url;

    @Schema(description = "Parent Menu ID")
    private MenuId parentMenuId;

    @Schema(description = "Sort Order")
    private Integer sortOrder;

    @Schema(description = "Authorities")
    private List<MenuAuthority> authorities;
}
