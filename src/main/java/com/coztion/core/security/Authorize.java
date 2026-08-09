package com.coztion.core.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.coztion.menu.model.AuthorityType;
import com.coztion.menu.model.MenuId;
import com.coztion.user.model.Authority;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {

    MenuId[] menuIds() default {};

    Authority[] authorities() default {};

    AuthorityType authorityType() default AuthorityType.READ;
}
