package coz.springboot.user.model;

import lombok.*;

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
