package coz.springboot.user.domain;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "user Information")
public class SessionUserInfo {

    @Schema(description = "Username", example = "admin")
    private String username;

    @Schema(description = "Authorities")
    private List<String> authorityCodes;
}
