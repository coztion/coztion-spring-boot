package com.coztion.user.model;

import jakarta.validation.constraints.NotBlank;
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

    @Schema(description = "Role Code", example = "ROLE_USER")
    @NotBlank(message = TranslationKey.Authority.Label.ROLE)
    private String roleCode;

    @Schema(description = "Data Status", example = "ENABLED")
    @NotNull(message = TranslationKey.Common.Label.DATA_STATUS)
    private String dataStatus;
}
