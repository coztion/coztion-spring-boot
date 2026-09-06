package com.coztion.authority.model;

import com.coztion.common.model.DataRecord;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Authority extends DataRecord {

    @Schema(description = "Authority Role")
    private String authorityRole;

    @Schema(description = "Authority Name")
    private String authorityName;
}
