package coz.springboot.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageRequest {

    @Schema(description = "Page Number", example = "1")
    protected Integer pageNum;

    @Schema(description = "Page Size", example = "20")
    protected Integer pageSize;
}
