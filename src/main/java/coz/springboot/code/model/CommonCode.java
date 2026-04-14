package coz.springboot.code.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.common.enums.YesOrNo;
import coz.springboot.common.model.DataRecord;

@Getter
@Setter
@NoArgsConstructor
public class CommonCode extends DataRecord {

    private String groupCode;

    private String commonCode;

    private String commonCodeName;

    private YesOrNo useYn = YesOrNo.YES;
}
