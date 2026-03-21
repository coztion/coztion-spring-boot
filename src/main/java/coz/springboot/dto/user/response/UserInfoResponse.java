package coz.springboot.dto.user.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Builder;
import lombok.Getter;

import coz.springboot.domain.user.SessionUser;

@Getter
@Builder
@Schema(description = "user Information")
public class UserInfoResponse {

    @Schema(description = "Username", example = "admin")
    private String username;

    @Schema(description = "Authorities")
    private List<String> authorityCodes;

    public static UserInfoResponse ofSessionUser(SessionUser sessionUser) {
        return UserInfoResponse.builder()
                .username(sessionUser.getUsername())
                .authorityCodes(sessionUser.getAuthorityCodes())
                .build();
    }
}
