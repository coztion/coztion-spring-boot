package com.coztion.menu.model;

import com.coztion.authority.model.AuthorityRole;
import com.coztion.authority.model.AuthorityType;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuAuthority {

    @Schema(description = "Menu Id")
    private MenuId menuId;

    @Schema(description = "Authority Role")
    private AuthorityRole authorityRole;

    @Schema(description = "Authority Type")
    private AuthorityType authorityType;
}
