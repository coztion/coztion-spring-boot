package coz.springboot.code.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

import coz.springboot.common.enums.YesOrNo;

@Getter
@Builder
public class CommonCodeFilter {

    private String groupCode;

    private List<String> groupCodes;

    private String commonCode;

    private YesOrNo useYn;

    public static CommonCodeFilter ofGroupCodesInUse(List<String> groupCodes) {
        return CommonCodeFilter.builder()
                .groupCodes(groupCodes)
                .useYn(YesOrNo.YES)
                .build();
    }
}
