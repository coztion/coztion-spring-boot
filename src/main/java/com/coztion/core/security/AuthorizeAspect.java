package com.coztion.core.security;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.coztion.authority.PermissionService;
import com.coztion.user.model.SessionUser;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

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

    private final PermissionService permissionService;

    @Before("@annotation(com.coztion.core.security.Authorize)")
    public void authorize(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Authorize authorize = signature.getMethod().getAnnotation(Authorize.class);

        String roleCode = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .map(SessionUser.class::cast)
                .orElseThrow(() -> new IllegalArgumentException(AUTHENTICATION_FAILURE_MESSAGE))
                .getRoleCode();

        List<String> grantedPermissionCodes = permissionService.getPermissionCodesByRoleCode(roleCode);

        boolean isNotAuthorized = Arrays.stream(authorize.permissionCodes())
                .noneMatch(permissionCode -> permissionCode.isGrantedIn(grantedPermissionCodes));
        if (isNotAuthorized) {
            throw new AccessDeniedException(AUTHORIZATION_FAILURE_MESSAGE);
        }
    }
}
