package com.coztion.user.model;

import java.util.List;

import com.coztion.common.model.DataRecord;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    private List<String> authorityRoles;
}
