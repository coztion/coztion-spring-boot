package com.coztion.menu.model;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDelete {

    @Schema(description = "Target Menu ID", hidden = true)
    private MenuId targetMenuId;

    @Schema(description = "Author", hidden = true)
    private String author;
}
