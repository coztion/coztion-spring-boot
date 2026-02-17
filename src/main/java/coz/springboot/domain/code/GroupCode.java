package coz.springboot.domain.code;

import java.util.List;

import lombok.*;

import coz.springboot.domain.common.DataRecord;

@Getter
@Setter
@NoArgsConstructor
public class GroupCode extends DataRecord {

    private String groupCode;

    private String groupCodeName;

    private Boolean isUse;

    private List<CommonCode> commonCodes;
}
