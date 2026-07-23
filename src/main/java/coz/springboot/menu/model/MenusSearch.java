package coz.springboot.menu.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;
import lombok.experimental.SuperBuilder;

import coz.springboot.common.enums.DataStatus;
import coz.springboot.common.model.Pagination;
import coz.springboot.user.model.Authority;

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
