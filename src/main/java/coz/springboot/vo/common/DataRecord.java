package coz.springboot.vo.common;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;

@Data
public class DataRecord {

    @Schema(description = "등록자")
    private String createdBy;

    @Schema(description = "등록일시")
    private LocalDateTime createdAt;

    @Schema(description = "수정자")
    private String updatedBy;

    @Schema(description = "수정일시")
    private LocalDateTime updatedAt;
}
