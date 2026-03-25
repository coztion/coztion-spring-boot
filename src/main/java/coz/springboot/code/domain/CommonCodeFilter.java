package coz.springboot.code.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonCodeFilter {

    private String groupCode;

    private String commonCode;

    private Boolean isUse;
}
