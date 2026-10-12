package com.coztion.file;

import jakarta.validation.Valid;

import com.coztion.common.dto.RestResponse;
import com.coztion.file.model.FileDelete;
import com.coztion.file.model.FileDownload;
import com.coztion.file.model.FileDownloadInfo;
import com.coztion.file.model.FileGroup;
import com.coztion.file.model.FileUpload;
import com.coztion.file.model.FilesSearch;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

@Tag(name = "File", description = "File API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @Operation(summary = "Get File Group")
    @GetMapping("/groups/{fileGroupId}")
    public RestResponse<FileGroup> getFileGroup(@ModelAttribute FilesSearch filesSearch) {
        FileGroup foundFileGroup = fileService.getFileGroup(filesSearch);
        return RestResponse.ofSuccess(foundFileGroup);
    }

    @Operation(summary = "Download File")
    @GetMapping("/{fileId}/download")
    public ResponseEntity<InputStreamResource> downloadFile(@ModelAttribute FileDownload fileDownload) {
        FileDownloadInfo fileDownloadInfo = fileService.downloadFile(fileDownload);
        return ResponseEntity.ok()
                .contentType(fileDownloadInfo.toMediaType())
                .contentLength(fileDownloadInfo.getFileSize())
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        fileDownloadInfo.toContentDisposition().toString())
                .body(new InputStreamResource(fileDownloadInfo.getInputStream()));
    }

    @Operation(summary = "View File", description = "Show image files inline, download other files")
    @GetMapping("/{fileId}/view")
    public ResponseEntity<InputStreamResource> viewFile(@ModelAttribute FileDownload fileDownload) {
        FileDownloadInfo fileDownloadInfo = fileService.downloadFile(fileDownload);
        return ResponseEntity.ok()
                .contentType(fileDownloadInfo.toMediaType())
                .contentLength(fileDownloadInfo.getFileSize())
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        fileDownloadInfo.toViewContentDisposition().toString())
                .body(new InputStreamResource(fileDownloadInfo.getInputStream()));
    }

    @Operation(summary = "Upload Files")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RestResponse<FileGroup> postFiles(@Valid @ModelAttribute FileUpload fileUpload) {
        FileGroup uploadedFileGroup = fileService.uploadFiles(fileUpload);
        return RestResponse.ofSuccess(uploadedFileGroup);
    }

    @Operation(summary = "Delete File")
    @DeleteMapping("/{fileId}")
    public RestResponse<FileGroup> deleteFile(@ModelAttribute FileDelete fileDelete) {
        FileGroup deletedFileGroup = fileService.deleteFile(fileDelete);
        return RestResponse.ofSuccess(deletedFileGroup);
    }
}
