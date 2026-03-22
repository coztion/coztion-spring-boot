package coz.springboot.vo.code;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.vo.common.DataRecord;

@Getter
@Setter
@NoArgsConstructor
public class CommonCode extends DataRecord {

    private String groupCode;

    private String commonCode;

    private String commonCodeName;

    private Boolean isUse;
}
