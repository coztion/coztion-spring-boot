package coz.springboot.code.model;

import java.util.List;

import lombok.*;

import coz.springboot.common.enums.YesOrNo;
import coz.springboot.common.model.DataRecord;

@Getter
@Setter
@NoArgsConstructor
public class GroupCode extends DataRecord {

    private String groupCode;

    private String groupCodeName;

    private YesOrNo useYn;

    private List<CommonCode> commonCodes;
}
