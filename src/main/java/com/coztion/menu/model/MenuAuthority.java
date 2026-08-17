package com.coztion.menu.model;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuAuthority {

    @Schema(description = "Menu Id")
    private String menuId;

    @Schema(description = "Authority Role")
    private String authorityRole;

    @Schema(description = "Authority Type")
    private String authorityType;
}
