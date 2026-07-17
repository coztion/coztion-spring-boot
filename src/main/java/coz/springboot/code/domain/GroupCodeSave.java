package coz.springboot.code.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.common.constant.Label;
import coz.springboot.common.enums.DataStatus;

@Getter
@Setter
@NoArgsConstructor
public class GroupCodeSave {

    @Schema(description = "Group Code")
    @NotBlank(message = Label.Code.GROUP_CODE)
    private String groupCode;

    @Schema(description = "Group Code Name")
    @NotBlank(message = Label.Code.GROUP_CODE_NAME)
    private String groupCodeName;

    @Schema(description = "Group Code Description")
    private String groupCodeDescription;

    @Schema(description = "Data Status")
    private DataStatus dataStatus = DataStatus.ENABLED;

    @Schema(description = "Sort Order")
    @NotNull(message = Label.Common.SORT_ORDER)
    @Min(value = 1, message = Label.Common.SORT_ORDER)
    private Integer sortOrder;

    @Schema(description = "Author", hidden = true)
    private String author;
}
