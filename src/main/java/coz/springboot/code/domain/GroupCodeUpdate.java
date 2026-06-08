package coz.springboot.code.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.common.constant.Label;
import coz.springboot.common.enums.YesOrNo;

@Getter
@Setter
@NoArgsConstructor
public class GroupCodeUpdate {

    @Schema(description = "Target Group Code", example = "USAGE", hidden = true)
    private String targetGroupCode;

    @Schema(description = "Group Code Name", example = "Usage")
    @NotBlank(message = Label.Code.GROUP_CODE_NAME)
    @Size(min = 1, max = 100, message = Label.Code.GROUP_CODE_NAME)
    private String groupCodeName;

    @Schema(description = "Use YN", example = "Y")
    @NotNull(message = Label.Common.USE_YN)
    private YesOrNo useYn;

    @Schema(description = "Author", hidden = true)
    private String author;

    public boolean hasUsageChanged(GroupCode groupCode) {
        return !useYn.equals(groupCode.getUseYn());
    }
}
