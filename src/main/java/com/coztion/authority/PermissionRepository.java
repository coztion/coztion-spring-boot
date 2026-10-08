package com.coztion.authority;

import java.util.Optional;

import com.coztion.authority.model.Permission;
import com.coztion.authority.model.PermissionDelete;
import com.coztion.authority.model.PermissionSave;
import com.coztion.authority.model.PermissionSearch;
import com.coztion.authority.model.PermissionUpdate;
import com.coztion.authority.model.PermissionsSearch;
import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionRepository {

    Page<Permission> selectPermissions(PermissionsSearch permissionsSearch);

    Optional<Permission> selectPermission(PermissionSearch permissionSearch);

    int insertPermission(PermissionSave permissionSave);

    int updatePermission(PermissionUpdate permissionUpdate);

    int deletePermission(PermissionDelete permissionDelete);

    int deleteRolePermissionsByPermissionDelete(PermissionDelete permissionDelete);
}
