package com.coztion.menu.model;

import com.coztion.common.enums.DataStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuSearch {

    @Schema(description = "Menu ID", hidden = true)
    private MenuId menuId;

    @Schema(description = "Data Status")
    private DataStatus dataStatus;

    @Schema(description = "Include Deleted Data")
    private boolean includeDeletedData;

    public static MenuSearch ofMenuId(MenuId menuId) {
        return MenuSearch.builder().menuId(menuId).build();
    }

    public static MenuSearch ofMenuIdIncludingDeletedData(MenuId menuId) {
        return MenuSearch.builder().menuId(menuId).includeDeletedData(true).build();
    }
}
