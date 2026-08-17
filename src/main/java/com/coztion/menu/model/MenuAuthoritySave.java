package com.coztion.menu.model;

import java.util.List;
import java.util.stream.Stream;

import com.coztion.authority.model.AuthorityRole;
import com.coztion.authority.model.AuthorityType;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuAuthoritySave {

    @Schema(description = "Menu Id")
    private String menuId;

    @Schema(description = "Authority Role")
    private String authorityRole;

    @Schema(description = "Authority Type")
    private String authorityType;

    public static List<MenuAuthoritySave> ofSystemWithReadAndWrite(String menuId) {
        return Stream.of(AuthorityType.READ, AuthorityType.WRITE)
                .map(authorityType -> MenuAuthoritySave.builder()
                        .menuId(menuId)
                        .authorityRole(AuthorityRole.SYSTEM.getValue())
                        .authorityType(authorityType.name())
                        .build())
                .toList();
    }
}
