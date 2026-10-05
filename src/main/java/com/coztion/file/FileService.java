package com.coztion.file;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.coztion.common.constant.TranslationKey;
import com.coztion.common.exception.RestException;
import com.coztion.file.model.FileDelete;
import com.coztion.file.model.FileDownload;
import com.coztion.file.model.FileDownloadInfo;
import com.coztion.file.model.FileGroup;
import com.coztion.file.model.FileInfo;
import com.coztion.file.model.FileSearch;
import com.coztion.file.model.FileStorageUpload;
import com.coztion.file.model.FileUpload;
import com.coztion.file.model.FilesSearch;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    private final FileStorage fileStorage;

    @Transactional(readOnly = true)
    public FileGroup getFileGroup(FilesSearch filesSearch) {
        List<FileInfo> fileInfos = fileRepository.selectFiles(filesSearch);
        return FileGroup.ofFileInfos(filesSearch.getFileGroupId(), fileInfos);
    }

    @Transactional(readOnly = true)
    public FileDownloadInfo downloadFile(FileDownload fileDownload) {
        FileSearch fileSearch = FileSearch.ofFileId(fileDownload.getFileId());
        FileInfo fileInfo = fileRepository
                .selectFile(fileSearch)
                .orElseThrow(() -> new RestException(
                        TranslationKey.Common.Message.NOT_FOUND_FAILURE, TranslationKey.File.Label.FILES));
        InputStream inputStream = fileStorage.download(fileInfo.getFilePath());
        return FileDownloadInfo.ofFileInfo(fileInfo, inputStream);
    }

    @Transactional
    public FileGroup uploadFiles(FileUpload fileUpload) {
        if (!fileUpload.hasTargets()) {
            throw new RestException(
                    TranslationKey.Common.Message.REQUIRED_VALIDATION_FAILURE, TranslationKey.File.Label.FILES);
        }

        if (fileUpload.hasFileGroupId()) {
            FilesSearch filesSearch = FilesSearch.ofFileGroupId(fileUpload.getFileGroupId());
            List<FileInfo> foundFileInfos = fileRepository.selectFiles(filesSearch);
            if (CollectionUtils.isEmpty(foundFileInfos)) {
                throw new RestException(
                        TranslationKey.Common.Message.NOT_FOUND_FAILURE, TranslationKey.File.Label.FILES);
            }
        }

        String fileGroupId = fileUpload.resolveFileGroupId();

        if (fileUpload.hasDeleteTargets()) {
            deleteFiles(fileUpload);
        }

        if (fileUpload.hasUploadTargets()) {
            storeFiles(fileGroupId, fileUpload);
        }

        return getFileGroup(FilesSearch.ofFileGroupId(fileGroupId));
    }

    @Transactional
    public FileGroup deleteFile(FileDelete fileDelete) {
        FileSearch fileSearch = FileSearch.ofFileId(fileDelete.getFileId());
        FileInfo fileInfo = fileRepository
                .selectFile(fileSearch)
                .orElseThrow(() -> new RestException(
                        TranslationKey.Common.Message.NOT_FOUND_FAILURE, TranslationKey.File.Label.FILES));

        int deletedCount = fileRepository.deleteFile(fileDelete);
        if (deletedCount == 0) {
            throw new RestException(TranslationKey.Common.Message.DELETE_FAILURE);
        }

        return getFileGroup(FilesSearch.ofFileGroupId(fileInfo.getFileGroupId()));
    }

    private void deleteFiles(FileUpload fileUpload) {
        int deletedCount = fileRepository.deleteFiles(fileUpload);
        if (deletedCount != fileUpload.getDeleteFileIds().size()) {
            throw new RestException(TranslationKey.Common.Message.DELETE_FAILURE);
        }
    }

    private void storeFiles(String fileGroupId, FileUpload fileUpload) {
        List<FileStorageUpload> fileStorageUploads = fileUpload.toFileStorageUploads(fileGroupId);

        List<FileStorageUpload> uploadedFileStorageUploads = new ArrayList<>();
        try {
            for (FileStorageUpload fileStorageUpload : fileStorageUploads) {
                fileStorage.upload(fileStorageUpload);
                uploadedFileStorageUploads.add(fileStorageUpload);
            }

            int createdCount = fileRepository.insertFiles(fileStorageUploads);
            if (createdCount != fileStorageUploads.size()) {
                throw new RestException(TranslationKey.Common.Message.CREATE_FAILURE);
            }
        } catch (RuntimeException e) {
            uploadedFileStorageUploads.forEach(uploadedFileStorageUpload -> deleteUploadedFile(uploadedFileStorageUpload, e));
            throw e;
        }
    }

    private void deleteUploadedFile(FileStorageUpload uploadedFileStorageUpload, RuntimeException cause) {
        try {
            fileStorage.delete(uploadedFileStorageUpload.getFilePath());
        } catch (RuntimeException e) {
            cause.addSuppressed(e);
        }
    }
}
