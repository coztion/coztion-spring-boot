package com.coztion.authority;

import com.coztion.authority.model.Role;
import com.coztion.authority.model.RolesSearch;
import com.github.pagehelper.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<Role> getRoles(RolesSearch rolesSearch) {
        rolesSearch.validatePageable();
        return roleRepository.selectRoles(rolesSearch);
    }
}
