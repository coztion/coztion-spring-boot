package com.coztion.authority.model;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthorityInfo {

    @Schema(description = "Authority Role")
    private String authorityRole;

    @Schema(description = "Authority Name")
    private String authorityName;
}
