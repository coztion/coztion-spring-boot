package coz.springboot.dto.code;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GroupCodeSearchParam {

    @Schema(description = "그룹코드목록")
    private List<String> groupCodes;

    @Schema(description = "그룹코드")
    private String groupCode;

    @Schema(description = "사용여부")
    private Boolean isUse;
}
