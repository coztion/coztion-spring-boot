package com.coztion.user;

import com.coztion.user.model.UserAuthoritiesSearch;
import com.coztion.user.model.UserAuthority;
import com.github.pagehelper.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAuthorityService {

    private final UserAuthorityRepository userAuthorityRepository;

    @Transactional(readOnly = true)
    public Page<UserAuthority> getUserAuthorities(UserAuthoritiesSearch userAuthoritiesSearch) {
        userAuthoritiesSearch.validatePageable();
        return userAuthorityRepository.selectUserAuthorities(userAuthoritiesSearch);
    }
}
