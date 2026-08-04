package com.coztion.menu.model;

import java.util.List;

import com.coztion.common.enums.DataStatus;
import com.coztion.common.model.Pagination;
import com.coztion.user.model.Authority;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MenusSearch extends Pagination {

    @Schema(description = "Menu Ids")
    private List<MenuId> menuIds;

    @Schema(description = "Menu Type", example = "GRP_NAV")
    private MenuType menuType;

    @Schema(description = "Data Status", example = "ENABLED")
    private DataStatus dataStatus;

    @Schema(description = "Authorities", hidden = true)
    private List<Authority> authorities;

    @Schema(description = "Include Authorities in Result")
    private boolean includeAuthorities;
}
