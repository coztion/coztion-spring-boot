package coz.springboot.domain.user;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFilter {

    private String username;

    public static UserFilter ofUsername(String username) {
        return UserFilter.builder().username(username).build();
    }
}
