package com.coztion.user;

import com.coztion.common.constant.TranslationKey;
import com.coztion.common.exception.RestException;
import com.coztion.file.FileService;
import com.coztion.file.model.FileGroup;
import com.coztion.file.model.FileInfo;
import com.coztion.file.model.FilesSearch;
import com.coztion.user.model.User;
import com.coztion.user.model.UserDelete;
import com.coztion.user.model.UserOperation;
import com.coztion.user.model.UserPasswordReset;
import com.coztion.user.model.UserProfileImage;
import com.coztion.user.model.UserProfileImageDelete;
import com.coztion.user.model.UserProfileImageUpdate;
import com.coztion.user.model.UserSave;
import com.coztion.user.model.UserSearch;
import com.coztion.user.model.UserUpdate;
import com.coztion.user.model.UsersSearch;
import com.github.pagehelper.Page;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final long PROFILE_IMAGE_MAX_SIZE = 5 * 1024 * 1024;

    private static final String PROFILE_IMAGE_MAX_SIZE_LABEL = "5MB";

    private final UserRepository userRepository;

    private final FileService fileService;

    private final PasswordEncoder passwordEncoder;

    @Value("${spring.security.password.initial}")
    private String initialPassword;

    @Transactional(readOnly = true)
    public User getUser(UserSearch userSearch) {
        return userRepository
                .selectUser(userSearch)
                .orElseThrow(() -> new RestException(
                        TranslationKey.Common.Message.NOT_FOUND_FAILURE, TranslationKey.User.Label.USER));
    }

    @Transactional(readOnly = true)
    public Page<User> getUsers(UsersSearch usersSearch) {
        usersSearch.validatePageable();
        return userRepository.selectUsers(usersSearch);
    }

    @Transactional
    public User saveUser(UserSave userSave) {
        UserSearch userSearch = UserSearch.ofUsername(userSave.getUsername());
        boolean userAlreadyExist = userRepository.selectUser(userSearch).isPresent();
        if (userAlreadyExist) {
            throw new RestException(
                    TranslationKey.Common.Message.DUPLICATE_FAILURE,
                    TranslationKey.User.Label.USERNAME,
                    userSave.getUsername());
        }

        userSave.encodePassword(passwordEncoder, initialPassword);
        int createdUserCount = userRepository.insertUser(userSave);
        if (createdUserCount == 0) {
            throw new RestException(TranslationKey.Common.Message.CREATE_FAILURE);
        }

        return userRepository
                .selectUser(userSearch)
                .orElseThrow(() -> new RestException(TranslationKey.Common.Message.CREATE_FAILURE));
    }

    @Transactional
    public void updateUser(UserUpdate userUpdate) {
        UserSearch userSearch = UserSearch.ofUsername(userUpdate.getUsername());
        getUser(userSearch);

        int updatedUserCount = userRepository.updateUser(userUpdate);
        if (updatedUserCount == 0) {
            throw new RestException(TranslationKey.Common.Message.UPDATE_FAILURE);
        }
    }

    @Transactional(readOnly = true)
    public UserProfileImage getUserProfileImage(UserSearch userSearch) {
        User foundUser = getUser(userSearch);
        if (!foundUser.hasProfileImage()) {
            return UserProfileImage.ofEmpty();
        }

        String profileImageFileGroupId = foundUser.getProfileImageFileGroupId();
        FilesSearch filesSearch = FilesSearch.ofFileGroupId(profileImageFileGroupId);
        return fileService
                .getFileGroup(filesSearch)
                .findLatestFile()
                .map(fileInfo -> UserProfileImage.ofFileInfo(profileImageFileGroupId, fileInfo))
                .orElseGet(() -> UserProfileImage.ofFileGroupId(profileImageFileGroupId));
    }

    @Transactional
    public void updateUserProfileImage(UserProfileImageUpdate userProfileImageUpdate) {
        UserSearch userSearch = UserSearch.ofUsername(userProfileImageUpdate.getUsername());
        getUser(userSearch);

        FilesSearch filesSearch = FilesSearch.ofFileGroupId(userProfileImageUpdate.getFileGroupId());
        FileGroup profileImageFileGroup = fileService.getFileGroup(filesSearch);
        FileInfo profileImage = profileImageFileGroup
                .findLatestFile()
                .orElseThrow(() -> new RestException(
                        TranslationKey.Common.Message.NOT_FOUND_FAILURE, TranslationKey.File.Label.FILES));

        if (!profileImage.isImage()) {
            throw new RestException(TranslationKey.User.Message.PROFILE_IMAGE_TYPE_VALIDATION_FAILURE);
        }

        if (profileImage.isLargerThan(PROFILE_IMAGE_MAX_SIZE)) {
            throw new RestException(
                    TranslationKey.Common.Message.MAX_SIZE_VALIDATION_FAILURE,
                    TranslationKey.User.Label.PROFILE_IMAGE,
                    PROFILE_IMAGE_MAX_SIZE_LABEL);
        }

        int updatedUserCount = userRepository.updateUserProfileImage(userProfileImageUpdate);
        if (updatedUserCount == 0) {
            throw new RestException(TranslationKey.Common.Message.UPDATE_FAILURE);
        }
    }

    @Transactional
    public void deleteUserProfileImage(UserProfileImageDelete userProfileImageDelete) {
        UserProfileImageUpdate userProfileImageUpdate = userProfileImageDelete.toUserProfileImageUpdate();
        UserSearch userSearch = UserSearch.ofUsername(userProfileImageUpdate.getUsername());
        getUser(userSearch);

        int updatedUserCount = userRepository.updateUserProfileImage(userProfileImageUpdate);
        if (updatedUserCount == 0) {
            throw new RestException(TranslationKey.Common.Message.UPDATE_FAILURE);
        }
    }

    @Transactional
    public void resetUserPassword(UserPasswordReset userPasswordReset) {
        UserSearch userSearch = UserSearch.ofUsername(userPasswordReset.getUsername());
        getUser(userSearch);

        UserUpdate userUpdate = userPasswordReset.toUserUpdate(passwordEncoder, initialPassword);
        int updatedUserCount = userRepository.updateUser(userUpdate);
        if (updatedUserCount == 0) {
            throw new RestException(TranslationKey.Common.Message.UPDATE_FAILURE);
        }
    }

    @Transactional
    public void deleteUser(UserDelete userDelete) {
        UserSearch userSearch = UserSearch.ofUsername(userDelete.getUsername());
        getUser(userSearch);

        int deletedUserCount = userRepository.deleteUser(userDelete);
        if (deletedUserCount == 0) {
            throw new RestException(TranslationKey.Common.Message.DELETE_FAILURE);
        }
    }

    @Transactional
    public void operateUsers(UserOperation userOperation) {
        if (userOperation.hasDeleteTargets()) {
            userOperation.getUserDeletes().forEach(this::deleteUser);
        }

        if (userOperation.hasUpdateTargets()) {
            userOperation.getUserUpdates().forEach(this::updateUser);
        }

        if (userOperation.hasSaveTargets()) {
            userOperation.getUserSaves().forEach(this::saveUser);
        }
    }
}
