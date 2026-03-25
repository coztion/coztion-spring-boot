package coz.springboot.code.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.code.model.GroupCodeFilter;

@Getter
@Setter
@NoArgsConstructor
public class GroupCodeGetRequest {

    @Schema(description = "Group Code List")
    private List<String> groupCodes;

    @Schema(description = "Group Code")
    private String groupCode;

    @Schema(description = "Usage Status", example = "true")
    private Boolean isUse;

    public GroupCodeFilter toDomain() {
        return GroupCodeFilter.builder()
                .groupCodes(groupCodes)
                .groupCode(groupCode)
                .isUse(isUse)
                .build();
    }
}
