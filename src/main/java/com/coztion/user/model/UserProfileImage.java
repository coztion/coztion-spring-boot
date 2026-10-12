package com.coztion.user.model;

import com.coztion.file.model.FileInfo;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserProfileImage {

    @Schema(description = "Profile Image File Group ID")
    private final String fileGroupId;

    @Schema(description = "Profile Image File ID")
    private final String fileId;

    public static UserProfileImage ofEmpty() {
        return new UserProfileImage(null, null);
    }

    public static UserProfileImage ofFileGroupId(String fileGroupId) {
        return new UserProfileImage(fileGroupId, null);
    }

    public static UserProfileImage ofFileInfo(String fileGroupId, FileInfo fileInfo) {
        return new UserProfileImage(fileGroupId, fileInfo.getFileId());
    }
}
