package coz.springboot.controller.menu;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import coz.springboot.dto.menu.MenuGetRequest;
import coz.springboot.dto.menu.MenuGetResponse;
import coz.springboot.dto.menu.MenuTreeResponse;
import coz.springboot.global.dto.response.RestResponse;
import coz.springboot.global.helper.SessionHolder;
import coz.springboot.service.menu.MenuService;
import coz.springboot.user.domain.Authority;
import coz.springboot.vo.menu.Menu;
import coz.springboot.vo.menu.MenuFilter;
import coz.springboot.vo.menu.MenuTree;

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
    public RestResponse<List<MenuTreeResponse>> getMenuTree() {
        List<Authority> authorities = SessionHolder.getAuthorities();
        List<MenuTree> menuTrees = menuService.getMenuTree(authorities);
        List<MenuTreeResponse> response =
                menuTrees.stream().map(MenuTreeResponse::ofDomain).toList();
        return RestResponse.ofSuccess(response);
    }
}
