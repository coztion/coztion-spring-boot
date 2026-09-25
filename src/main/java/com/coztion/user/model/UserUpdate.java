package com.coztion.user.model;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import com.coztion.common.constant.TranslationKey;
import com.coztion.common.model.OperationAuthor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdate extends OperationAuthor {

    @Schema(description = "Username")
    @NotBlank(message = TranslationKey.User.Label.USERNAME)
    private String username;

    @JsonIgnore
    @Schema(hidden = true)
    private String password;

    @Schema(description = "Authority Roles", example = "[\"ROLE_SYSTEM\", \"ROLE_ADMIN\", \"ROLE_USER\"]")
    @NotEmpty(message = TranslationKey.Authority.Label.AUTHORITY_ROLE)
    private List<String> authorityRoles;

    @Schema(description = "Data Status", example = "ENABLED")
    @NotNull(message = TranslationKey.Common.Label.DATA_STATUS)
    private String dataStatus;
}
