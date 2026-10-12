package com.coztion.user.model;

import com.coztion.common.model.DataRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User extends DataRecord {

    private String username;

    @JsonIgnore
    private String password;

    private String roleCode;

    private String profileImageFileGroupId;

    public boolean hasProfileImage() {
        return StringUtils.hasText(profileImageFileGroupId);
    }
}
