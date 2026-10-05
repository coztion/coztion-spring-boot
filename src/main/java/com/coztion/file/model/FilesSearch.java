package com.coztion.file.model;

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
public class FilesSearch {

    @Schema(description = "File Group ID")
    private String fileGroupId;

    public static FilesSearch ofFileGroupId(String fileGroupId) {
        return FilesSearch.builder().fileGroupId(fileGroupId).build();
    }
}
