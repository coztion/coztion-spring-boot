package coz.springboot.menu.domain;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import lombok.experimental.SuperBuilder;

import coz.springboot.common.domain.Pagination;
import coz.springboot.common.enums.DataStatus;
import coz.springboot.user.domain.Authority;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MenusSearch extends Pagination {

    @Schema(description = "Menu Type", example = "GRP_NAV")
    private MenuType menuType;

    @Schema(description = "Data Status", example = "ENABLED")
    private DataStatus dataStatus;

    @Schema(description = "Authorities", hidden = true)
    private List<Authority> authorities;
}
