package com.coztion.common.model;

import java.time.LocalDateTime;

import com.coztion.common.enums.DataStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataRecord {

    @Schema(description = "Data Status")
    private String dataStatus = DataStatus.ENABLED.getValue();

    @Schema(description = "Creator", example = "system")
    private String createdBy = "system";

    @Schema(description = "Created Date Time", example = "2024-01-01T12:00:00")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Schema(description = "Updator", example = "system")
    private String updatedBy = "system";

    @Schema(description = "Updated Date Time", example = "2024-01-02T12:00:00")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public boolean isEnabled() {
        return DataStatus.ofValue(dataStatus).isEnabled();
    }

    public boolean isDeleted() {
        return DataStatus.ofValue(dataStatus).isDeleted();
    }
}
