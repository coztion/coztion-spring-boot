package com.coztion.user.model;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAuthority {

    @Schema(description = "Username")
    private String username;

    @Schema(description = "Authority Role")
    private String authorityRole;
}
