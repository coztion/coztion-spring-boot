package com.coztion.code.model;

import jakarta.validation.constraints.NotBlank;

import com.coztion.common.constant.TranslationKey;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonCodeSearch {

    @Schema(description = "Group Code")
    @NotBlank(message = TranslationKey.Code.Label.GROUP_CODE)
    private String groupCode;

    @Schema(description = "Common Code")
    private String commonCode;

    @Schema(description = "Data Status")
    private String dataStatus;

    @Schema(description = "Include Deleted Data")
    private boolean includeDeletedData;

    public static CommonCodeSearch ofCode(String groupCode, String commonCode) {
        return CommonCodeSearch.builder()
                .groupCode(groupCode)
                .commonCode(commonCode)
                .build();
    }

    public static CommonCodeSearch ofCodeIncludingDeletedData(String groupCode, String commonCode) {
        return CommonCodeSearch.builder()
                .groupCode(groupCode)
                .commonCode(commonCode)
                .includeDeletedData(true)
                .build();
    }
}
