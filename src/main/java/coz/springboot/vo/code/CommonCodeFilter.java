package coz.springboot.vo.code;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonCodeFilter {

    private String groupCode;

    private String commonCode;

    private Boolean isUse;
}
