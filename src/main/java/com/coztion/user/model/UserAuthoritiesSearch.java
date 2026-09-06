package com.coztion.user.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserAuthoritiesSearch {

    @Schema(description = "Usernames")
    private List<String> usernames;

    public static UserAuthoritiesSearch ofUsernames(List<String> usernames) {
        return UserAuthoritiesSearch.builder().usernames(usernames).build();
    }
}
