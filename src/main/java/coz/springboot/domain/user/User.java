package coz.springboot.domain.user;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.enums.user.Authority;

@Getter
@Setter
@NoArgsConstructor
public class User {

    private String username;

    private String password;

    private List<Authority> authorities;
}
