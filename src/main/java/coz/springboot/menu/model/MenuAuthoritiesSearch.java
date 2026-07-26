package coz.springboot.menu.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class MenuAuthoritiesSearch {

    @Schema(description = "Menu Ids")
    private List<MenuId> menuIds;

    public static MenuAuthoritiesSearch ofMenuIds(List<MenuId> menuIds) {
        return MenuAuthoritiesSearch.builder().menuIds(menuIds).build();
    }
}
