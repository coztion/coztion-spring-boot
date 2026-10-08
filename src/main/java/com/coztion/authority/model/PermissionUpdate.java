package com.coztion.authority.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.coztion.common.constant.TranslationKey;
import com.coztion.common.model.OperationAuthor;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PermissionUpdate extends OperationAuthor {

    @Schema(description = "Permission Code", example = "USER_MANAGEMENT_READ")
    @NotBlank(message = TranslationKey.Authority.Label.PERMISSION_CODE)
    private String permissionCode;

    @Schema(description = "Permission Name")
    @NotBlank(message = TranslationKey.Authority.Label.PERMISSION_NAME)
    private String permissionName;

    @Schema(description = "Data Status", example = "ENABLED")
    @NotBlank(message = TranslationKey.Common.Label.DATA_STATUS)
    private String dataStatus;

    @Schema(description = "Sort Order")
    @NotNull(message = TranslationKey.Common.Label.SORT_ORDER)
    @Min(value = 1, message = TranslationKey.Common.Label.SORT_ORDER)
    private Integer sortOrder;
}
