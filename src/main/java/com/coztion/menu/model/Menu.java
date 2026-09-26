package com.coztion.menu.model;

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
    private String menuId;

    @Schema(description = "Menu Name")
    private String menuName;

    @Schema(description = "Menu Type")
    private String menuType;

    @Schema(description = "URL")
    private String url;

    @Schema(description = "Parent Menu ID")
    private String parentMenuId;

    @Schema(description = "Menu Permission Code")
    private String menuPermissionCode;

    @Schema(description = "Sort Order")
    private Integer sortOrder;
}
