package com.coztion.file.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {

    @Schema(description = "File ID")
    private String fileId;

    @Schema(description = "File Name")
    private String fileName;

    @Schema(description = "File Extension")
    private String fileExtension;

    @Schema(description = "File Content Type")
    private String fileContentType;

    @Schema(description = "File Size")
    private long fileSize;

    @JsonIgnore
    @Schema(hidden = true)
    private String fileGroupId;

    @JsonIgnore
    @Schema(hidden = true)
    private String filePath;
}
