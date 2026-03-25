package coz.springboot.code.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonCodeFilter {

    private String groupCode;

    private String commonCode;

    private Boolean isUse;
}
