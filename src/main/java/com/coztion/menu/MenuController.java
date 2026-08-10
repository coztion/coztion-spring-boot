package com.coztion.menu;

import java.util.List;

import jakarta.validation.Valid;

import com.coztion.authority.model.AuthorityRole;
import com.coztion.authority.model.AuthorityType;
import com.coztion.common.dto.PageResponse;
import com.coztion.common.dto.RestResponse;
import com.coztion.core.security.Authorize;
import com.coztion.menu.model.*;
import com.coztion.user.model.SessionUser;
import com.github.pagehelper.Page;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

@Tag(name = "Menu", description = "Menu API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "Get Menus")
    @GetMapping
    @Authorize(menuIds = MenuId.MENU_MANAGEMENT)
    public RestResponse<PageResponse<Menu>> getMenus(@Valid @ModelAttribute MenusSearch menusSearch) {
        Page<Menu> pagedMenus = menuService.getMenus(menusSearch);
        return RestResponse.ofSuccess(PageResponse.ofPage(pagedMenus));
    }

    @Operation(summary = "Get Menu")
    @GetMapping("/{menuId}")
    @Authorize(menuIds = MenuId.MENU_MANAGEMENT)
    public RestResponse<Menu> getMenu(@PathVariable MenuId menuId, @ModelAttribute MenuSearch menuSearch) {
        menuSearch.setMenuId(menuId);
        Menu foundMenu = menuService.getMenu(menuSearch);
        return RestResponse.ofSuccess(foundMenu);
    }

    @Operation(summary = "Get Menu Infos")
    @GetMapping("/menu-infos")
    public RestResponse<List<MenuInfo>> getMenuInfos(@AuthenticationPrincipal SessionUser sessionUser) {
        List<AuthorityRole> authorityRoles = sessionUser.getAuthorityRoleList();
        return RestResponse.ofSuccess(menuService.getMenuInfos(authorityRoles));
    }

    @Operation(summary = "Post Menu")
    @PostMapping
    @Authorize(menuIds = MenuId.MENU_MANAGEMENT, authorityType = AuthorityType.WRITE)
    public RestResponse<Menu> postMenu(
            @Valid @RequestBody MenuSave menuSave, @AuthenticationPrincipal SessionUser sessionUser) {
        menuSave.setAuthor(sessionUser.getUsername());
        Menu createdMenu = menuService.saveMenu(menuSave);
        return RestResponse.ofSuccess(createdMenu);
    }

    @Operation(summary = "Put Menu")
    @PutMapping("/{menuId}")
    @Authorize(menuIds = MenuId.MENU_MANAGEMENT, authorityType = AuthorityType.WRITE)
    public RestResponse<Void> putMenu(
            @PathVariable String menuId,
            @Valid @RequestBody MenuUpdate menuUpdate,
            @AuthenticationPrincipal SessionUser sessionUser) {
        menuUpdate.setTargetMenuId(menuId);
        menuUpdate.setAuthor(sessionUser.getUsername());
        menuService.updateMenu(menuUpdate);
        return RestResponse.ofSuccess(null);
    }

    @Operation(summary = "Delete Menu")
    @DeleteMapping("/{menuId}")
    @Authorize(menuIds = MenuId.MENU_MANAGEMENT, authorityType = AuthorityType.WRITE)
    public RestResponse<Boolean> deleteMenu(
            @PathVariable MenuId menuId, @AuthenticationPrincipal SessionUser sessionUser) {
        MenuDelete menuDelete = MenuDelete.builder()
                .targetMenuId(menuId)
                .author(sessionUser.getUsername())
                .build();
        menuService.deleteMenu(menuDelete);
        return RestResponse.ofSuccess(true);
    }
}
