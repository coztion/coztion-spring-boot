package com.coztion.menu.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class MenuAuthoritiesSearch {

    @Schema(description = "Menu Ids")
    private List<String> menuIds;

    public static MenuAuthoritiesSearch ofMenuIds(List<String> menuIds) {
        return MenuAuthoritiesSearch.builder().menuIds(menuIds).build();
    }
}
