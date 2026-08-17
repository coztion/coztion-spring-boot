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
public class CommonCodeDelete {

    @Schema(description = "Group Code")
    @NotBlank(message = TranslationKey.Code.Label.GROUP_CODE)
    private String groupCode;

    @Schema(description = "Common Code")
    @NotBlank(message = TranslationKey.Code.Label.COMMON_CODE)
    private String commonCode;

    @Schema(description = "Author", hidden = true)
    private String author;
}
