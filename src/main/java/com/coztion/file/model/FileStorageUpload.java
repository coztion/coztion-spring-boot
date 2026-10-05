package com.coztion.file.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FileStorageUpload {

    @Schema(description = "File ID")
    private final String fileId;

    @Schema(description = "File Group ID")
    private final String fileGroupId;

    @Schema(description = "File Name")
    private final String fileName;

    @Schema(description = "File Extension")
    private final String fileExtension;

    @Schema(description = "File Content Type")
    private final String fileContentType;

    @Schema(description = "File Size")
    private final long fileSize;

    @Schema(description = "File Path")
    private final String filePath;

    @Schema(description = "Author")
    private final String author;

    @Schema(description = "Multipart File")
    private final MultipartFile multipartFile;

    public static FileStorageUpload of(String fileGroupId, MultipartFile multipartFile, String author) {
        String fileId = UUID.randomUUID().toString();
        String fileName = Objects.requireNonNullElse(StringUtils.getFilename(multipartFile.getOriginalFilename()), "");
        String fileExtension = Objects.requireNonNullElse(StringUtils.getFilenameExtension(fileName), "");
        String fileContentType =
                Objects.requireNonNullElse(multipartFile.getContentType(), MediaType.APPLICATION_OCTET_STREAM_VALUE);
        String filePath = fileGroupId + "/" + fileId;
        return new FileStorageUpload(
                fileId,
                fileGroupId,
                fileName,
                fileExtension,
                fileContentType,
                multipartFile.getSize(),
                filePath,
                author,
                multipartFile);
    }

    public InputStream openInputStream() throws IOException {
        return multipartFile.getInputStream();
    }
}
