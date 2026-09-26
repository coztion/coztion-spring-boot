package com.coztion.authority;

import com.coztion.authority.model.Permission;
import com.coztion.authority.model.PermissionsSearch;
import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionRepository {

    Page<Permission> selectPermissions(PermissionsSearch permissionsSearch);
}
