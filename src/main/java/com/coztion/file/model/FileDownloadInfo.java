package com.coztion.file.model;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.http.ContentDisposition;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FileDownloadInfo {

    @Schema(description = "File Name")
    private final String fileName;

    @Schema(description = "File Content Type")
    private final String fileContentType;

    @Schema(description = "File Size")
    private final long fileSize;

    @Schema(description = "Input Stream")
    private final InputStream inputStream;

    public static FileDownloadInfo ofFileInfo(FileInfo fileInfo, InputStream inputStream) {
        return new FileDownloadInfo(
                fileInfo.getFileName(), fileInfo.getFileContentType(), fileInfo.getFileSize(), inputStream);
    }

    public MediaType toMediaType() {
        try {
            MediaType mediaType = MediaType.parseMediaType(fileContentType);
            return mediaType.isConcrete() ? mediaType : MediaType.APPLICATION_OCTET_STREAM;
        } catch (InvalidMediaTypeException e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    public ContentDisposition toContentDisposition() {
        return ContentDisposition.attachment()
                .filename(fileName, StandardCharsets.UTF_8)
                .build();
    }

    public ContentDisposition toViewContentDisposition() {
        if (!FileInfo.IMAGE_CONTENT_TYPES.contains(fileContentType)) {
            return toContentDisposition();
        }

        return ContentDisposition.inline()
                .filename(fileName, StandardCharsets.UTF_8)
                .build();
    }
}
