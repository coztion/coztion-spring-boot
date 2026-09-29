package com.coztion.user.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.coztion.common.constant.TranslationKey;
import com.coztion.common.model.OperationAuthor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSave extends OperationAuthor {

    @Schema(description = "Username")
    @NotBlank(message = TranslationKey.User.Label.USERNAME)
    @Size(min = 6, max = 50, message = TranslationKey.User.Label.USERNAME)
    private String username;

    @Schema(description = "Password (initial password is used when empty)")
    private String password;

    @Schema(description = "Role Code", example = "ROLE_USER")
    @NotBlank(message = TranslationKey.Authority.Label.ROLE)
    private String roleCode;

    @Schema(description = "Data Status", example = "ENABLED")
    @NotBlank(message = TranslationKey.Common.Label.DATA_STATUS)
    private String dataStatus;

    public void encodePassword(PasswordEncoder passwordEncoder, String initialPassword) {
        String rawPassword = StringUtils.hasText(password) ? password : initialPassword;
        this.password = passwordEncoder.encode(rawPassword);
    }
}
