package com.coztion.user.model;

import java.util.List;

import com.coztion.common.model.Pagination;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthoritiesSearch extends Pagination {

    @Schema(description = "Username")
    private String username;

    @Schema(description = "Usernames")
    private List<String> usernames;

    @Schema(description = "Authority Role")
    private String authorityRole;

    public static UserAuthoritiesSearch ofUsernames(List<String> usernames) {
        return UserAuthoritiesSearch.builder().usernames(usernames).build();
    }
}
