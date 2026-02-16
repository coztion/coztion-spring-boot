package coz.springboot.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchParam {

    @Schema(description = "사용자이름", example = "admin")
    private String username;

    public static UserSearchParam ofUsername(String username) {
        return UserSearchParam.builder().username(username).build();
    }
}
