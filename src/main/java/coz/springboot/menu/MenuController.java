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
import coz.springboot.menu.domain.MenuInfo;
import coz.springboot.menu.domain.MenuUpdate;
import coz.springboot.menu.domain.MenusSearch;
import coz.springboot.user.domain.Authority;

@Tag(name = "Menu", description = "Menu API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "Get Menus")
    @GetMapping
    public RestResponse<PageResponse<Menu>> getMenus(@Valid @ModelAttribute MenusSearch menusSearch) {
        Page<Menu> pagedMenus = menuService.getMenus(menusSearch);
        return RestResponse.ofSuccess(PageResponse.ofPage(pagedMenus));
    }

    @Operation(summary = "Get Menu Infos")
    @GetMapping("/menu-infos")
    public RestResponse<List<MenuInfo>> getMenuInfos() {
        List<Authority> authorities = SessionHolder.getAuthorities();
        return RestResponse.ofSuccess(menuService.getMenuInfos(authorities));
    }

    @Operation(summary = "Update Menu")
    @PatchMapping("/{menuId}")
    public RestResponse<Void> updateMenu(@PathVariable String menuId, @Valid @RequestBody MenuUpdate menuUpdate) {
        menuUpdate.setTargetMenuId(menuId);
        menuUpdate.setAuthor(SessionHolder.getUsername());
        menuService.updateMenu(menuUpdate);
        return RestResponse.ofSuccess(null);
    }
}
