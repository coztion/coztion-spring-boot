package coz.springboot.common.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataRecord {

    private String createdBy = "system";

    private LocalDateTime createdAt = LocalDateTime.now();

    private String updatedBy = "system";

    private LocalDateTime updatedAt = LocalDateTime.now();
}
