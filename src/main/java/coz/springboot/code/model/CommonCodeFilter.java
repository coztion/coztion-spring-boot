package coz.springboot.code.model;

import lombok.Builder;
import lombok.Getter;

import coz.springboot.common.enums.YesOrNo;

@Getter
@Builder
public class CommonCodeFilter {

    private String groupCode;

    private String commonCode;

    private YesOrNo useYn;
}
