package coz.springboot.code.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

import coz.springboot.common.enums.YesOrNo;

@Getter
@Builder
public class GroupCodeFilter {

    private List<String> groupCodes;

    private String groupCode;

    private String groupCodeName;

    private String commonCodeName;

    private YesOrNo useYn;
}
