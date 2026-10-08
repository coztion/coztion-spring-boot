package com.coztion.authority.model;

import jakarta.validation.constraints.NotBlank;

import com.coztion.common.constant.TranslationKey;
import com.coztion.common.model.OperationAuthor;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PermissionDelete extends OperationAuthor {

    @Schema(description = "Permission Code")
    @NotBlank(message = TranslationKey.Authority.Label.PERMISSION_CODE)
    private String permissionCode;
}
