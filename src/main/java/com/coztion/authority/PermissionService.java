package com.coztion.authority;

import com.coztion.authority.model.Permission;
import com.coztion.authority.model.PermissionsSearch;
import com.github.pagehelper.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    @Transactional(readOnly = true)
    public Page<Permission> getPermissions(PermissionsSearch permissionsSearch) {
        permissionsSearch.validatePageable();
        return permissionRepository.selectPermissions(permissionsSearch);
    }
}
