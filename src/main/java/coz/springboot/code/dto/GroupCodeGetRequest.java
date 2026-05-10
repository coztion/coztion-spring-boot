package coz.springboot.code.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.code.model.GroupCodeFilter;

@Getter
@Setter
@NoArgsConstructor
public class GroupCodeGetRequest {

    @Schema(description = "Group Code Name")
    private String groupCodeName;

    @Schema(description = "Common Code Name")
    private String commonCodeName;

    public GroupCodeFilter toGroupCodeFilter() {
        return GroupCodeFilter.builder()
                .groupCodeName(groupCodeName)
                .commonCodeName(commonCodeName)
                .build();
    }
}
