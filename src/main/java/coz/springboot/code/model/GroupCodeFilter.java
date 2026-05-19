package coz.springboot.code.model;

import java.util.List;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import coz.springboot.common.enums.YesOrNo;
import coz.springboot.common.model.PageFilter;

@Getter
@SuperBuilder
public class GroupCodeFilter extends PageFilter {

    private List<String> groupCodes;

    private String groupCode;

    private String groupCodeName;

    private String commonCodeName;

    private YesOrNo useYn;
}
