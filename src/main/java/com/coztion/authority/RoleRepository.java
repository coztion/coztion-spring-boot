package com.coztion.authority;

import java.util.Optional;

import com.coztion.authority.model.Role;
import com.coztion.authority.model.RoleDelete;
import com.coztion.authority.model.RoleSave;
import com.coztion.authority.model.RoleSearch;
import com.coztion.authority.model.RoleUpdate;
import com.coztion.authority.model.RolesSearch;
import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleRepository {

    Page<Role> selectRoles(RolesSearch rolesSearch);

    Optional<Role> selectRole(RoleSearch roleSearch);

    int insertRole(RoleSave roleSave);

    int insertRolePermissionsByRoleSave(RoleSave roleSave);

    int updateRole(RoleUpdate roleUpdate);

    int deleteRolePermissionsByRoleUpdate(RoleUpdate roleUpdate);

    int insertRolePermissionsByRoleUpdate(RoleUpdate roleUpdate);

    int deleteRole(RoleDelete roleDelete);

    int deleteRolePermissionsByRoleDelete(RoleDelete roleDelete);
}
