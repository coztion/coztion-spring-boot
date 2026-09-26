package com.coztion.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.coztion.authority.PermissionService;
import com.coztion.authority.model.Permission;
import com.coztion.authority.model.PermissionsSearch;
import com.coztion.common.constant.TranslationKey;
import com.coztion.common.exception.RestException;
import com.coztion.menu.model.*;
import com.github.pagehelper.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    private final PermissionService permissionService;

    @Transactional(readOnly = true)
    public Page<Menu> getMenus(MenusSearch menusSearch) {
        menusSearch.validatePageable();
        return menuRepository.selectMenus(menusSearch);
    }

    @Transactional(readOnly = true)
    public Menu getMenu(MenuSearch menuSearch) {
        return menuRepository
                .selectMenu(menuSearch)
                .orElseThrow(() -> new RestException(
                        TranslationKey.Common.Message.NOT_FOUND_FAILURE, TranslationKey.Menu.Label.MENU));
    }

    @Transactional(readOnly = true)
    public List<MenuInfo> getMenuInfos(String roleCode) {
        List<String> permissionCodes =
                permissionService.getPermissions(PermissionsSearch.ofEnabledRoleCode(roleCode)).stream()
                        .map(Permission::getPermissionCode)
                        .toList();
        if (CollectionUtils.isEmpty(permissionCodes)) {
            return List.of();
        }

        List<MenuInfo> menus = menuRepository.selectMenuInfos(permissionCodes);

        Map<String, MenuInfo> map = menus.stream().collect(Collectors.toMap(MenuInfo::getMenuId, menu -> menu));

        List<MenuInfo> roots = new ArrayList<>();
        for (MenuInfo menu : menus) {
            if (Objects.isNull(menu.getParentMenuId())) {
                roots.add(menu);
            } else {
                MenuInfo parent = map.get(menu.getParentMenuId());
                if (Objects.nonNull(parent)) {
                    if (Objects.isNull(parent.getSubMenus())) {
                        parent.setSubMenus(new ArrayList<>());
                    }
                    parent.getSubMenus().add(menu);
                }
            }
        }

        return roots;
    }

    @Transactional
    public Menu saveMenu(MenuSave menuSave) {
        MenuSearch searchForExistingMenu = MenuSearch.ofMenuId(menuSave.getMenuId());
        Optional<Menu> optionalExistingMenu = menuRepository.selectMenu(searchForExistingMenu);
        if (optionalExistingMenu.isPresent()) {
            throw new RestException(
                    TranslationKey.Common.Message.DUPLICATE_FAILURE,
                    TranslationKey.Menu.Label.MENU,
                    menuSave.getMenuId());
        }

        int createdCount = menuRepository.insertMenu(menuSave);
        if (createdCount == 0) {
            throw new RestException(TranslationKey.Common.Message.CREATE_FAILURE);
        }

        MenuSearch menuSearch = MenuSearch.ofMenuId(menuSave.getMenuId());
        Optional<Menu> foundMenu = menuRepository.selectMenu(menuSearch);

        return foundMenu.orElseThrow(() -> new RestException(TranslationKey.Common.Message.CREATE_FAILURE));
    }

    @Transactional
    public void updateMenu(MenuUpdate menuUpdate) {
        MenuSearch menuSearch = MenuSearch.ofMenuId(menuUpdate.getMenuId());
        menuRepository
                .selectMenu(menuSearch)
                .orElseThrow(() -> new RestException(
                        TranslationKey.Common.Message.NOT_FOUND_FAILURE, TranslationKey.Menu.Label.MENU));

        int updatedMenuCount = menuRepository.updateMenu(menuUpdate);
        if (updatedMenuCount == 0) {
            throw new RestException(TranslationKey.Common.Message.UPDATE_FAILURE);
        }
    }

    @Transactional
    public void deleteMenu(MenuDelete menuDelete) {
        MenuSearch menuSearch = MenuSearch.ofMenuId(menuDelete.getMenuId());
        Optional<Menu> foundMenu = menuRepository.selectMenu(menuSearch);
        if (foundMenu.isEmpty()) {
            throw new RestException(TranslationKey.Common.Message.NOT_FOUND_FAILURE, TranslationKey.Menu.Label.MENU);
        }

        MenusSearch subMenusSearch =
                MenusSearch.builder().parentMenuId(menuDelete.getMenuId()).build();
        List<Menu> foundSubMenus = menuRepository.selectMenus(subMenusSearch);
        if (!CollectionUtils.isEmpty(foundSubMenus)) {
            throw new RestException(TranslationKey.Menu.Message.MENU_DELETE_FAILURE_BY_SUB_MENU);
        }

        int deletedCount = menuRepository.deleteMenu(menuDelete);
        if (deletedCount == 0) {
            throw new RestException(TranslationKey.Common.Message.DELETE_FAILURE);
        }
    }

    @Transactional
    public void operateMenus(MenuOperate menuOperate) {
        if (menuOperate.hasDeleteTargets()) {
            List<String> deleteMenuIds = menuOperate.getDeleteMenuIds();
            MenusSearch deleteMenusSearch = MenusSearch.ofMenuIds(deleteMenuIds);
            List<Menu> deleteMenus = menuRepository.selectMenus(deleteMenusSearch);
            Map<String, String> parentMenuIdsOfDeleteMenus = deleteMenus.stream()
                    .collect(
                            HashMap::new,
                            (map, menu) -> map.put(menu.getMenuId(), menu.getParentMenuId()),
                            HashMap::putAll);

            menuOperate
                    .getMenuDeletesSortedByHierarchy(parentMenuIdsOfDeleteMenus)
                    .forEach(this::deleteMenu);
        }

        if (menuOperate.hasUpdateTargets()) {
            menuOperate.getMenuUpdates().forEach(this::updateMenu);
        }

        if (menuOperate.hasSaveTargets()) {
            menuOperate.getMenuSavesSortedByHierarchy().forEach(this::saveMenu);
        }
    }
}
