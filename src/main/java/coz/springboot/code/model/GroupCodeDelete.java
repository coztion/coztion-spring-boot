package coz.springboot.code.model;

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
public class GroupCodeDelete {

    @Schema(description = "Target Group Code", hidden = true)
    private String targetGroupCode;

    @Schema(description = "Author", hidden = true)
    private String author;
}
