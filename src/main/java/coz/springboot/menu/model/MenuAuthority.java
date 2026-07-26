package coz.springboot.menu.model;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.user.model.Authority;

@Getter
@Setter
@NoArgsConstructor
public class MenuAuthority {

    @Schema(description = "Menu Id")
    private MenuId menuId;

    @Schema(description = "Authority")
    private Authority authority;
}
