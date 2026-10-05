package com.coztion.file.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FileGroup {

    @Schema(description = "File Group ID")
    private final String fileGroupId;

    @Schema(description = "Files")
    private final List<FileInfo> files;

    public static FileGroup ofFileInfos(String fileGroupId, List<FileInfo> fileInfos) {
        return new FileGroup(fileGroupId, fileInfos);
    }
}
