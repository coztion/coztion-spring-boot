package coz.springboot.menu.model;

import lombok.Builder;
import lombok.Getter;

import coz.springboot.common.enums.YesOrNo;

@Getter
@Builder
public class MenuUpdate {

    private String targetMenuId;

    private String menuName;

    private YesOrNo useYn;

    private String author;
}
