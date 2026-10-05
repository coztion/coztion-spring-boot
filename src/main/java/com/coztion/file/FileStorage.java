package com.coztion.file;

import java.io.IOException;
import java.io.InputStream;

import com.coztion.common.constant.TranslationKey;
import com.coztion.common.exception.RestException;
import com.coztion.file.model.FileStorageUpload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.http.ContentStreamProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FileStorage {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    public void upload(FileStorageUpload fileStorageUpload) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileStorageUpload.getFilePath())
                .contentType(fileStorageUpload.getFileContentType())
                .contentLength(fileStorageUpload.getFileSize())
                .build();
        ContentStreamProvider contentStreamProvider =
                ContentStreamProvider.fromInputStreamSupplier(() -> openInputStream(fileStorageUpload));
        s3Client.putObject(
                putObjectRequest,
                RequestBody.fromContentProvider(
                        contentStreamProvider, fileStorageUpload.getFileSize(), fileStorageUpload.getFileContentType()));
    }

    public InputStream download(String filePath) {
        GetObjectRequest getObjectRequest =
                GetObjectRequest.builder().bucket(bucket).key(filePath).build();
        try {
            return s3Client.getObject(getObjectRequest);
        } catch (NoSuchKeyException e) {
            throw new RestException(TranslationKey.Common.Message.NOT_FOUND_FAILURE, TranslationKey.File.Label.FILES);
        }
    }

    public void delete(String filePath) {
        DeleteObjectRequest deleteObjectRequest =
                DeleteObjectRequest.builder().bucket(bucket).key(filePath).build();
        s3Client.deleteObject(deleteObjectRequest);
    }

    private InputStream openInputStream(FileStorageUpload fileStorageUpload) {
        try {
            return fileStorageUpload.openInputStream();
        } catch (IOException e) {
            throw new RestException(TranslationKey.Common.Message.CREATE_FAILURE);
        }
    }
}
