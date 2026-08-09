package com.coztion.menu.model;

import com.coztion.user.model.Authority;

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

    @Schema(description = "Authority")
    private Authority authority;

    @Schema(description = "Authority Type")
    private AuthorityType authorityType;
}
