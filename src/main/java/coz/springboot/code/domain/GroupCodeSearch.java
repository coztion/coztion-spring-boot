package coz.springboot.code.domain;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.common.enums.YesOrNo;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupCodeSearch {

    @Schema(description = "Group Code", hidden = true)
    private String groupCode;

    @Schema(description = "Usage")
    private YesOrNo useYn;
}
