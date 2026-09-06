package com.coztion.authority;

import com.coztion.authority.model.Authority;
import com.coztion.authority.model.AuthoritiesSearch;
import com.github.pagehelper.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    @Transactional(readOnly = true)
    public Page<Authority> getAuthorities(AuthoritiesSearch authoritiesSearch) {
        authoritiesSearch.validatePageable();
        return authorityRepository.selectAuthorities(authoritiesSearch);
    }
}
