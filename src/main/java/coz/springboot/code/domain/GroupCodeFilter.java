package coz.springboot.code.domain;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupCodeFilter {

    private List<String> groupCodes;

    private String groupCode;

    private Boolean isUse;
}
