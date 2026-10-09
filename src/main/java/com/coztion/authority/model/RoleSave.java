package com.coztion.authority.model;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.coztion.common.constant.TranslationKey;
import com.coztion.common.model.OperationAuthor;

import org.springframework.util.CollectionUtils;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleSave extends OperationAuthor {

    @Schema(description = "Role Code", example = "ROLE_USER")
    @NotBlank(message = TranslationKey.Authority.Label.ROLE_CODE)
    private String roleCode;

    @Schema(description = "Role Name")
    @NotBlank(message = TranslationKey.Authority.Label.ROLE_NAME)
    private String roleName;

    @Schema(description = "Data Status", example = "ENABLED")
    @NotBlank(message = TranslationKey.Common.Label.DATA_STATUS)
    private String dataStatus;

    @Schema(description = "Sort Order")
    @NotNull(message = TranslationKey.Common.Label.SORT_ORDER)
    @Min(value = 1, message = TranslationKey.Common.Label.SORT_ORDER)
    private Integer sortOrder;

    @Schema(description = "Permission Codes")
    private List<String> permissionCodes;

    public boolean hasPermissionCodes() {
        return !CollectionUtils.isEmpty(permissionCodes);
    }
}
