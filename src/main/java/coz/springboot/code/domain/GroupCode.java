package coz.springboot.code.domain;

import java.util.List;

import lombok.*;

import coz.springboot.global.vo.DataRecord;

@Getter
@Setter
@NoArgsConstructor
public class GroupCode extends DataRecord {

    private String groupCode;

    private String groupCodeName;

    private Boolean isUse;

    private List<CommonCode> commonCodes;
}
