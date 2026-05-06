package coz.springboot.menu;

import java.util.List;

import jakarta.validation.Valid;

import com.github.pagehelper.Page;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import coz.springboot.common.dto.PageResponse;
import coz.springboot.common.dto.RestResponse;
import coz.springboot.common.helper.SessionHolder;
import coz.springboot.menu.dto.MenuGetRequest;
import coz.springboot.menu.dto.MenuUpdateRequest;
import coz.springboot.menu.model.Menu;
import coz.springboot.menu.model.MenuFilter;
import coz.springboot.menu.model.MenuTree;
import coz.springboot.menu.model.MenuUpdate;
import coz.springboot.user.model.Authority;

@Tag(name = "Menu", description = "Menu API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "Get Menus", description = "Search menus based on search filter")
    @GetMapping
    public RestResponse<PageResponse<Menu>> getMenus(MenuGetRequest request) {
        MenuFilter menuFilter = request.toMenuFilter();
        return RestResponse.ofSuccess(PageResponse.of(menuService.getMenus(menuFilter)));
    }

    @Operation(summary = "Get Menu Tree", description = "Get hierarchical menu tree for user")
    @GetMapping("/menu-tree")
    public RestResponse<List<MenuTree>> getMenuTree() {
        List<Authority> authorities = SessionHolder.getAuthorities();
        return RestResponse.ofSuccess(menuService.getMenuTree(authorities));
    }

    @Operation(summary = "Get Menu Ids", description = "Get menu ids for user")
    @GetMapping("/ids")
    public RestResponse<List<String>> getMenuIds() {
        List<Authority> authorities = SessionHolder.getAuthorities();
        Page<Menu> menus = menuService.getMenus(MenuFilter.ofAuthorities(authorities));
        List<String> menuIds = menus.stream().map(Menu::getMenuId).toList();
        return RestResponse.ofSuccess(menuIds);
    }

    @Operation(summary = "Update Menu", description = "Update menu information")
    @PatchMapping("/{menuId}")
    public RestResponse<Void> updateMenu(@PathVariable String menuId, @Valid @RequestBody MenuUpdateRequest request) {
        MenuUpdate update = request.toMenuUpdate(menuId, SessionHolder.getUsername());
        menuService.updateMenu(update);
        return RestResponse.ofSuccess(null);
    }
}
