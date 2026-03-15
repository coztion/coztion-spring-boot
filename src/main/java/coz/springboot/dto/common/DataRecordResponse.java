package coz.springboot.dto.common;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import coz.springboot.domain.common.DataRecord;

@Getter
@SuperBuilder
public class DataRecordResponse {

    @Schema(description = "Creator", example = "system")
    private String createdBy;

    @Schema(description = "Created Date Time", example = "2024-01-01T12:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Updator", example = "system")
    private String updatedBy;

    @Schema(description = "Updated Date Time", example = "2024-01-02T12:00:00")
    private LocalDateTime updatedAt;

    public abstract static class DataRecordResponseBuilder<
            C extends DataRecordResponse, B extends DataRecordResponseBuilder<C, B>> {
        public B dataRecord(DataRecord dataRecord) {
            this.createdBy = dataRecord.getCreatedBy();
            this.createdAt = dataRecord.getCreatedAt();
            this.updatedBy = dataRecord.getUpdatedBy();
            this.updatedAt = dataRecord.getUpdatedAt();
            return self();
        }
    }
}
