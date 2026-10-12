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

    private String roleCode;

    public static SessionUser ofUser(User user) {
        return SessionUser.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roleCode(user.getRoleCode())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of((GrantedAuthority) () -> roleCode);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public SessionUserInfo toPublicInfo(List<String> permissionCodes, UserProfileImage userProfileImage) {
        return SessionUserInfo.builder()
                .username(username)
                .roleCode(roleCode)
                .permissionCodes(permissionCodes)
                .profileImageFileGroupId(userProfileImage.getFileGroupId())
                .profileImageFileId(userProfileImage.getFileId())
                .build();
    }
}
