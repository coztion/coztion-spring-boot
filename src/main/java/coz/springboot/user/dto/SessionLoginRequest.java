package coz.springboot.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SessionLoginRequest {

    @Schema(description = "Username")
    private String username;

    @Schema(description = "Password")
    private String password;
}
