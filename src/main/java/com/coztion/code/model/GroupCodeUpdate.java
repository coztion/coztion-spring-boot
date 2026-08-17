package com.coztion.code.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.coztion.common.constant.TranslationKey;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GroupCodeUpdate {

    @Schema(description = "Group Code", example = "USAGE")
    @NotBlank(message = TranslationKey.Code.Label.GROUP_CODE)
    private String groupCode;

    @Schema(description = "Group Code Name", example = "Usage")
    @NotBlank(message = TranslationKey.Code.Label.GROUP_CODE_NAME)
    @Size(min = 1, max = 100, message = TranslationKey.Code.Label.GROUP_CODE_NAME)
    private String groupCodeName;

    @Schema(description = "Group Code Description")
    private String groupCodeDescription;

    @Schema(description = "Data Status", example = "ENABLED")
    @NotBlank(message = TranslationKey.Common.Label.DATA_STATUS)
    private String dataStatus;

    @Schema(description = "Author", hidden = true)
    private String author;

    public boolean hasDataStatusChanged(GroupCode groupCode) {
        return !dataStatus.equals(groupCode.getDataStatus());
    }
}
