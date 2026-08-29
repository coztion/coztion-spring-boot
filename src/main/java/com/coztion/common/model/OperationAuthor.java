package com.coztion.common.model;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OperationAuthor {

    @Schema(description = "Author", hidden = true)
    private String author;
}
