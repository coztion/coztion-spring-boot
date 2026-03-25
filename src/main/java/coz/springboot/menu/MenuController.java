package coz.springboot.menu;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import coz.springboot.global.dto.RestResponse;
import coz.springboot.global.helper.SessionHolder;
import coz.springboot.menu.dto.MenuGetRequest;
import coz.springboot.menu.dto.MenuGetResponse;
import coz.springboot.menu.dto.MenuTreeGetResponse;
import coz.springboot.menu.model.Menu;
import coz.springboot.menu.model.MenuFilter;
import coz.springboot.menu.model.MenuTree;
import coz.springboot.user.model.Authority;

@Tag(name = "Menu", description = "Menu API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "Get Menus", description = "Search menus based on search filter")
    @GetMapping
    public RestResponse<List<MenuGetResponse>> getMenus(MenuGetRequest request) {
        MenuFilter menuFilter = request.toDomain();
        List<Menu> menus = menuService.getMenus(menuFilter);
        List<MenuGetResponse> response =
                menus.stream().map(MenuGetResponse::ofDomain).toList();
        return RestResponse.ofSuccess(response);
    }

    @Operation(summary = "Get Menu Tree", description = "Get hierarchical menu tree for current user")
    @GetMapping("/menu-tree")
    public RestResponse<List<MenuTreeGetResponse>> getMenuTree() {
        List<Authority> authorities = SessionHolder.getAuthorities();
        List<MenuTree> menuTrees = menuService.getMenuTree(authorities);
        List<MenuTreeGetResponse> response =
                menuTrees.stream().map(MenuTreeGetResponse::ofDomain).toList();
        return RestResponse.ofSuccess(response);
    }
}
