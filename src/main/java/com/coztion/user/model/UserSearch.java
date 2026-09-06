package com.coztion.user.model;

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

    private String username;

    public static UserSearch ofUsername(String username) {
        return UserSearch.builder().username(username).build();
    }
}
