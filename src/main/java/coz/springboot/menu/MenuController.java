package coz.springboot.menu;

import java.util.List;

import jakarta.validation.Valid;

import com.github.pagehelper.Page;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import coz.springboot.common.dto.PageResponse;
import coz.springboot.common.dto.RestResponse;
import coz.springboot.core.security.Authorize;
import coz.springboot.menu.model.*;
import coz.springboot.user.model.Authority;
import coz.springboot.user.model.SessionUser;

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

    @Operation(summary = "Get Menu Infos")
    @GetMapping("/menu-infos")
    public RestResponse<List<MenuInfo>> getMenuInfos(@AuthenticationPrincipal SessionUser sessionUser) {
        List<Authority> authorities = sessionUser.getAuthorityList();
        return RestResponse.ofSuccess(menuService.getMenuInfos(authorities));
    }

    @Operation(summary = "Update Menu")
    @PatchMapping("/{menuId}")
    @Authorize(menuIds = MenuId.MENU_MANAGEMENT)
    public RestResponse<Void> updateMenu(
            @PathVariable String menuId,
            @Valid @RequestBody MenuUpdate menuUpdate,
            @AuthenticationPrincipal SessionUser sessionUser) {
        menuUpdate.setTargetMenuId(menuId);
        menuUpdate.setAuthor(sessionUser.getUsername());
        menuService.updateMenu(menuUpdate);
        return RestResponse.ofSuccess(null);
    }
}
