package com.coztion.authority;

import com.coztion.authority.model.Role;
import com.coztion.authority.model.RoleDelete;
import com.coztion.authority.model.RoleOperation;
import com.coztion.authority.model.RoleSave;
import com.coztion.authority.model.RoleSearch;
import com.coztion.authority.model.RoleUpdate;
import com.coztion.authority.model.RolesSearch;
import com.coztion.common.constant.TranslationKey;
import com.coztion.common.exception.RestException;
import com.coztion.user.UserService;
import com.coztion.user.model.User;
import com.coztion.user.model.UsersSearch;
import com.github.pagehelper.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final UserService userService;

    @Transactional(readOnly = true)
    public Page<Role> getRoles(RolesSearch rolesSearch) {
        rolesSearch.validatePageable();
        return roleRepository.selectRoles(rolesSearch);
    }

    @Transactional(readOnly = true)
    public Role getRole(RoleSearch roleSearch) {
        return roleRepository
                .selectRole(roleSearch)
                .orElseThrow(() -> new RestException(
                        TranslationKey.Common.Message.NOT_FOUND_FAILURE, TranslationKey.Authority.Label.ROLE));
    }

    @Transactional
    public Role saveRole(RoleSave roleSave) {
        RoleSearch roleSearch = RoleSearch.ofRoleCode(roleSave.getRoleCode());
        boolean roleAlreadyExist = roleRepository.selectRole(roleSearch).isPresent();
        if (roleAlreadyExist) {
            throw new RestException(
                    TranslationKey.Common.Message.DUPLICATE_FAILURE,
                    TranslationKey.Authority.Label.ROLE_CODE,
                    roleSave.getRoleCode());
        }

        int createdCount = roleRepository.insertRole(roleSave);
        if (createdCount == 0) {
            throw new RestException(TranslationKey.Common.Message.CREATE_FAILURE);
        }

        if (roleSave.hasPermissionCodes()) {
            roleRepository.insertRolePermissionsByRoleSave(roleSave);
        }

        return roleRepository
                .selectRole(roleSearch)
                .orElseThrow(() -> new RestException(TranslationKey.Common.Message.CREATE_FAILURE));
    }

    @Transactional
    public void updateRole(RoleUpdate roleUpdate) {
        RoleSearch roleSearch = RoleSearch.ofRoleCode(roleUpdate.getRoleCode());
        getRole(roleSearch);

        int updatedCount = roleRepository.updateRole(roleUpdate);
        if (updatedCount == 0) {
            throw new RestException(TranslationKey.Common.Message.UPDATE_FAILURE);
        }

        if (roleUpdate.isPermissionCodesReplaced()) {
            roleRepository.deleteRolePermissionsByRoleUpdate(roleUpdate);

            if (roleUpdate.hasPermissionCodes()) {
                roleRepository.insertRolePermissionsByRoleUpdate(roleUpdate);
            }
        }
    }

    @Transactional
    public void deleteRole(RoleDelete roleDelete) {
        RoleSearch roleSearch = RoleSearch.ofRoleCode(roleDelete.getRoleCode());
        Role role = getRole(roleSearch);

        Page<User> roleUsers = userService.getUsers(UsersSearch.ofRoleCode(roleDelete.getRoleCode()));
        if (!roleUsers.isEmpty()) {
            throw new RestException(TranslationKey.Authority.Message.ROLE_DELETE_FAILURE_BY_USER);
        }

        if (role.hasPermissionCodes()) {
            roleRepository.deleteRolePermissionsByRoleDelete(roleDelete);
        }

        int deletedCount = roleRepository.deleteRole(roleDelete);
        if (deletedCount == 0) {
            throw new RestException(TranslationKey.Common.Message.DELETE_FAILURE);
        }
    }

    @Transactional
    public void operateRoles(RoleOperation roleOperation) {
        if (roleOperation.hasDeleteTargets()) {
            roleOperation.getRoleDeletes().forEach(this::deleteRole);
        }

        if (roleOperation.hasUpdateTargets()) {
            roleOperation.getRoleUpdates().forEach(this::updateRole);
        }

        if (roleOperation.hasSaveTargets()) {
            roleOperation.getRoleSaves().forEach(this::saveRole);
        }
    }
}
