package coz.springboot.menu.model;

import java.util.List;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import coz.springboot.common.enums.YesOrNo;
import coz.springboot.common.model.PageFilter;
import coz.springboot.user.model.Authority;

@Getter
@SuperBuilder
public class MenuFilter extends PageFilter {

    private MenuType menuType;

    private YesOrNo useYn;

    private List<Authority> authorities;

    public static MenuFilter ofAuthorities(List<Authority> authorities) {
        return MenuFilter.builder().authorities(authorities).build();
    }
}
