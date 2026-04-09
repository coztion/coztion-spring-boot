package coz.springboot.menu.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuUpdate {

    private String targetMenuId;

    private String menuName;

    private Boolean isUse;

    private String author;
}
