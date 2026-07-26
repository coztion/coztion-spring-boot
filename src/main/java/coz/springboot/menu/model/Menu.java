package coz.springboot.menu.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.common.model.DataRecord;
import coz.springboot.user.model.Authority;

@Getter
@Setter
@NoArgsConstructor
public class Menu extends DataRecord {

    @Schema(description = "Menu ID")
    private MenuId menuId;

    @Schema(description = "Menu Name")
    private String menuName;

    @Schema(description = "Menu Type")
    private MenuType menuType;

    @Schema(description = "Parent Menu ID")
    private MenuId parentMenuId;

    @Schema(description = "Sort Order")
    private Integer sortOrder;

    @Schema(description = "Authorities")
    private List<Authority> authorities;
}
