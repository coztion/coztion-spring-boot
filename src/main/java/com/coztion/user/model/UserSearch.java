package com.coztion.user.model;

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
public class UserSearch {

    @Schema(description = "Username", hidden = true)
    private String username;

    public static UserSearch ofUsername(String username) {
        return UserSearch.builder().username(username).build();
    }
}
