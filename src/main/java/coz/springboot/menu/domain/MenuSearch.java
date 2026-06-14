package coz.springboot.menu.domain;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import lombok.experimental.SuperBuilder;

import coz.springboot.common.domain.Pagination;
import coz.springboot.common.enums.YesOrNo;
import coz.springboot.user.domain.Authority;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MenuSearch extends Pagination {

    @Schema(description = "Menu Type", example = "GRP_NAV")
    private MenuType menuType;

    @Schema(description = "Usage Status", example = "Y")
    private YesOrNo useYn;

    @Schema(description = "Authorities", hidden = true)
    private List<Authority> authorities;

    public static MenuSearch ofAuthorities(List<Authority> authorities) {
        return MenuSearch.builder().authorities(authorities).build();
    }
}
