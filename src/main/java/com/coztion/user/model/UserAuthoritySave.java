package com.coztion.user.model;

import java.util.List;

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
public class UserAuthoritySave {

    @Schema(description = "Username")
    private String username;

    @Schema(description = "Authority Role")
    private String authorityRole;

    public static List<UserAuthoritySave> ofUsernameAndAuthorityRoles(String username, List<String> authorityRoles) {
        return authorityRoles.stream()
                .map(authorityRole -> UserAuthoritySave.builder()
                        .username(username)
                        .authorityRole(authorityRole)
                        .build())
                .toList();
    }
}
