package com.coztion.authority;

import com.coztion.authority.model.Role;
import com.coztion.authority.model.RolesSearch;
import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleRepository {

    Page<Role> selectRoles(RolesSearch rolesSearch);
}
