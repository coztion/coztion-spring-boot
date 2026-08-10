package com.coztion.user.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SessionUser implements UserDetails {

    private String username;

    private String password;

    private List<String> authorityRoles;

    public static SessionUser ofUser(User user) {
        return SessionUser.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorityRoles(user.getAuthorityRoles().stream()
                        .map(AuthorityRole::getAuthorityRole)
                        .toList())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityRoles.stream()
                .map(role -> (GrantedAuthority) () -> role)
                .toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public SessionUserInfo toPublicInfo() {
        return SessionUserInfo.builder()
                .username(username)
                .authorityRoles(authorityRoles)
                .build();
    }

    public List<AuthorityRole> getAuthorityRoleList() {
        return authorityRoles.stream().map(AuthorityRole::ofValue).toList();
    }
}
