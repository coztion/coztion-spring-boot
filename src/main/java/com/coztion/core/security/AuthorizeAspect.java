package com.coztion.core.security;

import java.util.*;

import com.coztion.authority.model.AuthorityRole;
import com.coztion.authority.model.AuthorityType;
import com.coztion.menu.MenuService;
import com.coztion.menu.model.Menu;
import com.coztion.menu.model.MenuAuthority;
import com.coztion.menu.model.MenuId;
import com.coztion.menu.model.MenusSearch;
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
        AuthorityRole[] authorityRoles = authorize.authorityRoles();
        AuthorityType authorityType = authorize.authorityType();

        List<AuthorityRole> requiredAuthorityRoles =
                extractRequiredAuthorityRoles(List.of(menuIds), List.of(authorityRoles), authorityType);

        List<AuthorityRole> userAuthorityRoles = Optional.ofNullable(
                        SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .map(SessionUser.class::cast)
                .orElseThrow(() -> new IllegalArgumentException(AUTHENTICATION_FAILURE_MESSAGE))
                .getAuthorityRoleList();

        boolean isNotAuthorized = !validateAuthorityRole(requiredAuthorityRoles, userAuthorityRoles);
        if (isNotAuthorized) {
            throw new AccessDeniedException(AUTHORIZATION_FAILURE_MESSAGE);
        }
    }

    private List<AuthorityRole> extractRequiredAuthorityRoles(
            List<MenuId> menuIds, List<AuthorityRole> authorityRoles, AuthorityType authorityType) {
        boolean isMenuIdsEmpty = CollectionUtils.isEmpty(menuIds);
        if (isMenuIdsEmpty) {
            return authorityRoles;
        }

        List<AuthorityRole> accessibleMenuAuthorityRoles = getAccessibleMenuAuthorityRoles(menuIds, authorityType);

        boolean hasAuthorityRoles = !CollectionUtils.isEmpty(authorityRoles);
        if (hasAuthorityRoles) {
            return authorityRoles.stream()
                    .filter(accessibleMenuAuthorityRoles::contains)
                    .toList();
        }

        return accessibleMenuAuthorityRoles;
    }

    private List<AuthorityRole> getAccessibleMenuAuthorityRoles(List<MenuId> menuIds, AuthorityType authorityType) {
        MenusSearch menuSearch = MenusSearch.builder()
                .menuIds(menuIds.stream().map(MenuId::getValue).toList())
                .includeAuthorities(true)
                .build();
        List<Menu> menus = menuService.getMenus(menuSearch);
        return menus.stream()
                .map(Menu::getAuthorities)
                .flatMap(Collection::stream)
                .filter(menuAuthority ->
                        AuthorityType.ofValue(menuAuthority.getAuthorityType()).equals(authorityType))
                .map(MenuAuthority::getAuthorityRole)
                .distinct()
                .map(AuthorityRole::ofValue)
                .toList();
    }

    private boolean validateAuthorityRole(
            List<AuthorityRole> requiredAuthorityRoles, List<AuthorityRole> userAuthorityRoles) {
        return requiredAuthorityRoles.stream().anyMatch(userAuthorityRoles::contains);
    }
}
