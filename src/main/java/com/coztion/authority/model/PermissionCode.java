package com.coztion.authority.model;

import java.util.Collection;

import com.coztion.common.enums.ValueEnum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PermissionCode implements ValueEnum {
    MAIN_READ("MAIN_READ"),
    CODE_MANAGEMENT_READ("CODE_MANAGEMENT_READ"),
    CODE_MANAGEMENT_WRITE("CODE_MANAGEMENT_WRITE"),
    MENU_MANAGEMENT_READ("MENU_MANAGEMENT_READ"),
    MENU_MANAGEMENT_WRITE("MENU_MANAGEMENT_WRITE"),
    USER_MANAGEMENT_READ("USER_MANAGEMENT_READ"),
    USER_MANAGEMENT_WRITE("USER_MANAGEMENT_WRITE"),
    AUTHORITY_MANAGEMENT_READ("AUTHORITY_MANAGEMENT_READ"),
    AUTHORITY_MANAGEMENT_WRITE("AUTHORITY_MANAGEMENT_WRITE"),
    TRANSLATION_MANAGEMENT_READ("TRANSLATION_MANAGEMENT_READ"),
    TRANSLATION_MANAGEMENT_WRITE("TRANSLATION_MANAGEMENT_WRITE"),
    DEV_READ("DEV_READ");

    private final String value;

    public boolean isGrantedIn(Collection<String> grantedPermissionCodes) {
        return grantedPermissionCodes.contains(value);
    }
}
