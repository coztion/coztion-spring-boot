package com.coztion.user.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "user Information")
public class SessionUserInfo {

    @Schema(description = "Username", example = "admin")
    private String username;

    @Schema(description = "Role Code", example = "ROLE_ADMIN")
    private String roleCode;

    @Schema(description = "Permission Codes", example = "[\"MAIN_READ\"]")
    private List<String> permissionCodes;
}
