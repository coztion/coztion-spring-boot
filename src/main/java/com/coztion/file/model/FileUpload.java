package com.coztion.file.model;

import java.util.List;
import java.util.UUID;

import com.coztion.common.model.OperationAuthor;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileUpload extends OperationAuthor {

    @Schema(description = "File Group ID")
    private String fileGroupId;

    @Schema(description = "Upload Files")
    private List<MultipartFile> uploadFiles;

    @Schema(description = "Delete File IDs")
    private List<String> deleteFileIds;

    public boolean hasFileGroupId() {
        return StringUtils.hasText(fileGroupId);
    }

    public boolean hasUploadTargets() {
        return !CollectionUtils.isEmpty(uploadFiles);
    }

    public boolean hasDeleteTargets() {
        return !CollectionUtils.isEmpty(deleteFileIds);
    }

    public boolean hasTargets() {
        return hasUploadTargets() || hasDeleteTargets();
    }

    public boolean hasLargerFileThan(long maxFileSize) {
        return hasUploadTargets() && uploadFiles.stream().anyMatch(uploadFile -> uploadFile.getSize() > maxFileSize);
    }

    public String resolveFileGroupId() {
        return hasFileGroupId() ? fileGroupId : UUID.randomUUID().toString();
    }

    public List<FileStorageUpload> toFileStorageUploads(String fileGroupId) {
        return uploadFiles.stream()
                .map(multipartFile -> FileStorageUpload.of(fileGroupId, multipartFile, getAuthor()))
                .toList();
    }
}
