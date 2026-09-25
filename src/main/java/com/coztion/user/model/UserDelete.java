package com.coztion.user.model;

import jakarta.validation.constraints.NotBlank;

import com.coztion.common.constant.TranslationKey;
import com.coztion.common.model.OperationAuthor;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDelete extends OperationAuthor {

    @Schema(description = "Username")
    @NotBlank(message = TranslationKey.User.Label.USERNAME)
    private String username;
}
