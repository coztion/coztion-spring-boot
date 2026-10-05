package com.coztion.file;

import java.util.List;
import java.util.Optional;

import com.coztion.file.model.FileDelete;
import com.coztion.file.model.FileInfo;
import com.coztion.file.model.FileSearch;
import com.coztion.file.model.FileStorageUpload;
import com.coztion.file.model.FileUpload;
import com.coztion.file.model.FilesSearch;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileRepository {

    List<FileInfo> selectFiles(FilesSearch filesSearch);

    Optional<FileInfo> selectFile(FileSearch fileSearch);

    int insertFiles(List<FileStorageUpload> fileStorageUploads);

    int deleteFiles(FileUpload fileUpload);

    int deleteFile(FileDelete fileDelete);
}
