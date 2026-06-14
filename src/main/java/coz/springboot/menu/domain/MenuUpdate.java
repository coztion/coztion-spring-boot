package coz.springboot.menu.domain;

import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import coz.springboot.common.enums.YesOrNo;

@Getter
@Setter
@NoArgsConstructor
public class MenuUpdate {

    @Schema(description = "Target Menu ID", hidden = true)
    private String targetMenuId;

    @Schema(description = "Menu Name")
    @Size(min = 1, max = 100)
    private String menuName;

    @Schema(description = "In Use")
    private YesOrNo useYn;

    @Schema(description = "Author", hidden = true)
    private String author;
}
