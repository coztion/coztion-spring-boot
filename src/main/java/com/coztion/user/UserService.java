package com.coztion.user;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.coztion.common.constant.TranslationKey;
import com.coztion.common.exception.RestException;
import com.coztion.user.model.User;
import com.coztion.user.model.UserAuthoritiesSearch;
import com.coztion.user.model.UserAuthority;
import com.coztion.user.model.UserAuthoritySave;
import com.coztion.user.model.UserDelete;
import com.coztion.user.model.UserOperation;
import com.coztion.user.model.UserPasswordReset;
import com.coztion.user.model.UserSave;
import com.coztion.user.model.UserSearch;
import com.coztion.user.model.UserUpdate;
import com.coztion.user.model.UsersSearch;
import com.github.pagehelper.Page;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserAuthorityRepository userAuthorityRepository;

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
        Page<User> users = userRepository.selectUsers(usersSearch);

        boolean hasUsers = !CollectionUtils.isEmpty(users);
        if (hasUsers && usersSearch.isIncludeAuthorityRoles()) {
            List<String> usernames = users.stream().map(User::getUsername).toList();
            List<UserAuthority> userAuthorities =
                    userAuthorityRepository.selectUserAuthorities(UserAuthoritiesSearch.ofUsernames(usernames));
            Map<String, List<String>> usernameAuthorityRoles = userAuthorities.stream()
                    .collect(Collectors.groupingBy(
                            UserAuthority::getUsername,
                            Collectors.mapping(UserAuthority::getAuthorityRole, Collectors.toList())));
            users.forEach(
                    user -> user.setAuthorityRoles(usernameAuthorityRoles.getOrDefault(user.getUsername(), List.of())));
        }

        return users;
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

        userAuthorityRepository.insertUserAuthorities(
                UserAuthoritySave.ofUsernameAndAuthorityRoles(userSave.getUsername(), userSave.getAuthorityRoles()));

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

        userAuthorityRepository.deleteUserAuthoritiesByUsername(userUpdate.getUsername());
        userAuthorityRepository.insertUserAuthorities(
                UserAuthoritySave.ofUsernameAndAuthorityRoles(userUpdate.getUsername(), userUpdate.getAuthorityRoles()));
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

        userAuthorityRepository.deleteUserAuthoritiesByUsername(userDelete.getUsername());
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
