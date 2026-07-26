package coz.springboot.core.security;

import java.util.*;

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

import coz.springboot.menu.MenuService;
import coz.springboot.menu.model.Menu;
import coz.springboot.menu.model.MenuId;
import coz.springboot.menu.model.MenusSearch;
import coz.springboot.user.model.Authority;
import coz.springboot.user.model.SessionUser;

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

    @Before("@annotation(coz.springboot.core.security.Authorize)")
    public void authorize(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Authorize authorize = signature.getMethod().getAnnotation(Authorize.class);

        MenuId[] menuIds = authorize.menuIds();
        Authority[] authorities = authorize.authorities();

        List<Authority> requiredAuthorities = extractRequiredAuthorities(List.of(menuIds), List.of(authorities));

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

    private List<Authority> extractRequiredAuthorities(List<MenuId> menuIds, List<Authority> authorities) {
        boolean isMenuIdsEmpty = CollectionUtils.isEmpty(menuIds);
        if (isMenuIdsEmpty) {
            return authorities;
        }

        List<Authority> accessibleMenuAuthorities = getAccessibleMenuAuthorities(menuIds);

        boolean hasAuthorities = !CollectionUtils.isEmpty(authorities);
        if (hasAuthorities) {
            return authorities.stream()
                    .filter(accessibleMenuAuthorities::contains)
                    .toList();
        }

        return accessibleMenuAuthorities;
    }

    private List<Authority> getAccessibleMenuAuthorities(List<MenuId> menuIds) {
        MenusSearch menuSearch =
                MenusSearch.builder().menuIds(menuIds).includeAuthorities(true).build();
        List<Menu> menus = menuService.getMenus(menuSearch);
        return menus.stream()
                .map(Menu::getAuthorities)
                .flatMap(Collection::stream)
                .distinct()
                .toList();
    }

    private boolean validateAuthority(List<Authority> requiredAuthorities, List<Authority> userAuthorities) {
        return requiredAuthorities.stream().anyMatch(userAuthorities::contains);
    }
}
