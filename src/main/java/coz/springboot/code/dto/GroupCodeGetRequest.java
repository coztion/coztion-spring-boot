package coz.springboot.code.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.code.model.GroupCodeFilter;
import coz.springboot.common.enums.YesOrNo;

@Getter
@Setter
@NoArgsConstructor
public class GroupCodeGetRequest {

    @Schema(description = "Group Code List")
    private List<String> groupCodes;

    @Schema(description = "Group Code")
    private String groupCode;

    @Schema(description = "Usage Status", example = "Y")
    private String useYn;

    public GroupCodeFilter toGroupCodeFilter() {
        return GroupCodeFilter.builder()
                .groupCodes(groupCodes)
                .groupCode(groupCode)
                .useYn(YesOrNo.ofValue(useYn))
                .build();
    }
}
