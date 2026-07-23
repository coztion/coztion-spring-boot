package coz.springboot.code.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.common.constant.Message;
import coz.springboot.common.enums.DataStatus;

@Getter
@Setter
@NoArgsConstructor
public class GroupCodeUpdate {

    @Schema(description = "Target Group Code", example = "USAGE", hidden = true)
    private String targetGroupCode;

    @Schema(description = "Group Code Name", example = "Usage")
    @NotBlank(message = Message.Code.Label.GROUP_CODE_NAME)
    @Size(min = 1, max = 100, message = Message.Code.Label.GROUP_CODE_NAME)
    private String groupCodeName;

    @Schema(description = "Group Code Description")
    private String groupCodeDescription;

    @Schema(description = "Data Status", example = "ENABLED")
    @NotNull(message = Message.Common.Label.DATA_STATUS)
    private DataStatus dataStatus;

    @Schema(description = "Author", hidden = true)
    private String author;

    public boolean hasDataStatusChanged(GroupCode groupCode) {
        return !dataStatus.equals(groupCode.getDataStatus());
    }
}
