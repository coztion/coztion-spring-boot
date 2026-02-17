package coz.springboot.domain.code;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonCodeFilter {

    private String groupCode;

    private String commonCode;

    private Boolean isUse;
}
