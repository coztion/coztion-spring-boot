package coz.springboot.menu;

import java.util.List;

import jakarta.validation.Valid;

import com.github.pagehelper.Page;

import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import coz.springboot.common.dto.PageResponse;
import coz.springboot.common.dto.RestResponse;
import coz.springboot.common.helper.SessionHolder;
import coz.springboot.menu.domain.Menu;
import coz.springboot.menu.domain.MenuSearch;
import coz.springboot.menu.domain.MenuTree;
import coz.springboot.menu.domain.MenuUpdate;
import coz.springboot.user.domain.Authority;

@Tag(name = "Menu", description = "Menu API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "Get Menus", description = "Search menus based on search filter")
    @GetMapping
    public RestResponse<PageResponse<Menu>> getMenus(@Valid @ModelAttribute MenuSearch menuSearch) {
        Page<Menu> pagedMenus = menuService.searchMenus(menuSearch);
        return RestResponse.ofSuccess(PageResponse.ofPage(pagedMenus));
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
        Page<Menu> menus = menuService.searchMenus(MenuSearch.ofAuthorities(authorities));
        List<String> menuIds = menus.stream().map(Menu::getMenuId).toList();
        return RestResponse.ofSuccess(menuIds);
    }

    @Operation(summary = "Update Menu", description = "Update menu information")
    @PatchMapping("/{menuId}")
    public RestResponse<Void> updateMenu(@PathVariable String menuId, @Valid @RequestBody MenuUpdate menuUpdate) {
        menuUpdate.setTargetMenuId(menuId);
        menuUpdate.setAuthor(SessionHolder.getUsername());
        menuService.updateMenu(menuUpdate);
        return RestResponse.ofSuccess(null);
    }
}
