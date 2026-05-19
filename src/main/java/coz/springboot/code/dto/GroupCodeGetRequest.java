package coz.springboot.code.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.code.model.GroupCodeFilter;
import coz.springboot.common.dto.PageRequest;
import coz.springboot.common.enums.YesOrNo;

@Getter
@Setter
@NoArgsConstructor
public class GroupCodeGetRequest extends PageRequest {

    @Schema(description = "Group Code Name")
    private String groupCodeName;

    @Schema(description = "Common Code Name")
    private String commonCodeName;

    @Schema(description = "Usage")
    private String useYn;

    public GroupCodeFilter toGroupCodeFilter() {
        return GroupCodeFilter.builder()
                .groupCodeName(groupCodeName)
                .commonCodeName(commonCodeName)
                .useYn(YesOrNo.ofValue(useYn))
                .page(this)
                .build();
    }
}
