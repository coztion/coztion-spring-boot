package com.coztion.menu.model;

import java.util.List;

import com.coztion.authority.model.AuthorityRole;
import com.coztion.common.enums.DataStatus;
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
    private MenuId menuId;

    @Schema(description = "Menu Ids")
    private List<MenuId> menuIds;

    @Schema(description = "Menu Name")
    private String menuName;

    @Schema(description = "Menu Type", example = "GRP_NAV")
    private MenuType menuType;

    @Schema(description = "Data Status", example = "ENABLED")
    private DataStatus dataStatus;

    @Schema(description = "Authority Roles", hidden = true)
    private List<AuthorityRole> authorityRoles;

    @Schema(description = "Include Authorities in Result")
    private boolean includeAuthorities;

    @Schema(description = "Include Deleted Data")
    private boolean includeDeletedData;

    public static MenusSearch ofMenuIdsIncludingDeletedData(List<MenuId> menuIds) {
        return MenusSearch.builder().menuIds(menuIds).includeDeletedData(true).build();
    }
}
