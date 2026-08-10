package com.coztion.core.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.coztion.authority.model.AuthorityRole;
import com.coztion.authority.model.AuthorityType;
import com.coztion.menu.model.MenuId;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {

    MenuId[] menuIds() default {};

    AuthorityRole[] authorityRoles() default {};

    AuthorityType authorityType() default AuthorityType.READ;
}
