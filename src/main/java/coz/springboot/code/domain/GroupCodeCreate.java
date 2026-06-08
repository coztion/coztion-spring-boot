package coz.springboot.code.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.common.constant.Label;
import coz.springboot.common.enums.YesOrNo;

@Getter
@Setter
@NoArgsConstructor
public class GroupCodeCreate {

    @Schema(description = "Group Code")
    @NotBlank(message = Label.Code.GROUP_CODE)
    private String groupCode;

    @Schema(description = "Group Code Name")
    @NotBlank(message = Label.Code.GROUP_CODE_NAME)
    private String groupCodeName;

    @Schema(description = "Usage Status")
    private YesOrNo useYn = YesOrNo.YES;

    @Schema(description = "Sort Order")
    @NotNull(message = Label.Common.SORT_ORDER)
    @Min(value = 1, message = Label.Common.SORT_ORDER)
    private Integer sortOrder;

    @Schema(description = "Author", hidden = true)
    private String author;
}
