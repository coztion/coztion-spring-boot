package com.coztion.user.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {

    private String username;

    private String password;

    private List<String> authorityRoles;
}
