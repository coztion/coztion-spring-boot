package com.coztion.menu.model;

import java.util.List;

import com.coztion.common.model.Pagination;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MenusSearch extends Pagination {

    @Schema(description = "Menu Id")
    private String menuId;

    @Schema(description = "Menu Ids")
    private List<String> menuIds;

    @Schema(description = "Parent Menu Id", hidden = true)
    private String parentMenuId;

    @Schema(description = "Menu Name")
    private String menuName;

    @Schema(description = "Menu Type", example = "GRP_NAV")
    private String menuType;

    @Schema(description = "Data Status", example = "ENABLED")
    private String dataStatus;

    @Schema(description = "Authority Roles", hidden = true)
    private List<String> authorityRoles;

    @Schema(description = "Include Authorities in Result")
    private boolean includeAuthorities;

    public static MenusSearch ofMenuIds(List<String> menuIds) {
        return MenusSearch.builder().menuIds(menuIds).build();
    }
}
