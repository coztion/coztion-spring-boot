package com.coztion.core.security;

import java.util.*;

import com.coztion.menu.MenuService;
import com.coztion.menu.model.AuthorityType;
import com.coztion.menu.model.Menu;
import com.coztion.menu.model.MenuAuthority;
import com.coztion.menu.model.MenuId;
import com.coztion.menu.model.MenusSearch;
import com.coztion.user.model.Authority;
import com.coztion.user.model.SessionUser;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuthorizeAspect {

    private static final String AUTHENTICATION_FAILURE_MESSAGE =
            "[AuthorizeAspect] Authorization failed because user is not authenticated.";

    private static final String AUTHORIZATION_FAILURE_MESSAGE =
            "[AuthorizeAspect] Authorization failed because user is not authorized.";

    private final MenuService menuService;

    @Before("@annotation(com.coztion.core.security.Authorize)")
    public void authorize(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Authorize authorize = signature.getMethod().getAnnotation(Authorize.class);

        MenuId[] menuIds = authorize.menuIds();
        Authority[] authorities = authorize.authorities();
        AuthorityType authorityType = authorize.authorityType();

        List<Authority> requiredAuthorities =
                extractRequiredAuthorities(List.of(menuIds), List.of(authorities), authorityType);

        List<Authority> userAuthorities = Optional.ofNullable(
                        SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .map(SessionUser.class::cast)
                .orElseThrow(() -> new IllegalArgumentException(AUTHENTICATION_FAILURE_MESSAGE))
                .getAuthorityList();

        boolean isNotAuthorized = !validateAuthority(requiredAuthorities, userAuthorities);
        if (isNotAuthorized) {
            throw new AccessDeniedException(AUTHORIZATION_FAILURE_MESSAGE);
        }
    }

    private List<Authority> extractRequiredAuthorities(
            List<MenuId> menuIds, List<Authority> authorities, AuthorityType authorityType) {
        boolean isMenuIdsEmpty = CollectionUtils.isEmpty(menuIds);
        if (isMenuIdsEmpty) {
            return authorities;
        }

        List<Authority> accessibleMenuAuthorities = getAccessibleMenuAuthorities(menuIds, authorityType);

        boolean hasAuthorities = !CollectionUtils.isEmpty(authorities);
        if (hasAuthorities) {
            return authorities.stream()
                    .filter(accessibleMenuAuthorities::contains)
                    .toList();
        }

        return accessibleMenuAuthorities;
    }

    private List<Authority> getAccessibleMenuAuthorities(List<MenuId> menuIds, AuthorityType authorityType) {
        MenusSearch menuSearch =
                MenusSearch.builder().menuIds(menuIds).includeAuthorities(true).build();
        List<Menu> menus = menuService.getMenus(menuSearch);
        return menus.stream()
                .map(Menu::getAuthorities)
                .flatMap(Collection::stream)
                .filter(menuAuthority -> menuAuthority.getAuthorityType() == authorityType)
                .map(MenuAuthority::getAuthority)
                .distinct()
                .toList();
    }

    private boolean validateAuthority(List<Authority> requiredAuthorities, List<Authority> userAuthorities) {
        return requiredAuthorities.stream().anyMatch(userAuthorities::contains);
    }
}
