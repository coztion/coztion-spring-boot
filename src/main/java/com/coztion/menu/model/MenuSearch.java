package com.coztion.menu.model;

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
    private String menuId;

    @Schema(description = "Data Status")
    private String dataStatus;

    public static MenuSearch ofMenuId(String menuId) {
        return MenuSearch.builder().menuId(menuId).build();
    }
}
