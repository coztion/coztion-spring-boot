package com.coztion.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private final MenuAuthorityRepository menuAuthorityRepository;

    @Transactional(readOnly = true)
    public Page<Menu> getMenus(MenusSearch menusSearch) {
        menusSearch.validatePageable();
        Page<Menu> menus = menuRepository.selectMenus(menusSearch);

        boolean hasMenus = !CollectionUtils.isEmpty(menus);
        if (hasMenus && menusSearch.isIncludeAuthorities()) {
            List<String> menuIds = menus.stream().map(Menu::getMenuId).toList();
            List<MenuAuthority> menuAuthorities =
                    menuAuthorityRepository.selectMenuAuthorities(MenuAuthoritiesSearch.ofMenuIds(menuIds));
            Map<String, List<MenuAuthority>> menuIdMenuAuthorities =
                    menuAuthorities.stream().collect(Collectors.groupingBy(MenuAuthority::getMenuId));
            menus.forEach(menu -> menu.setAuthorities(menuIdMenuAuthorities.getOrDefault(menu.getMenuId(), List.of())));
        }

        return menus;
    }

    @Transactional(readOnly = true)
    public Menu getMenu(MenuSearch menuSearch) {
        return menuRepository
                .selectMenu(menuSearch)
                .orElseThrow(() -> new RestException(
                        TranslationKey.Common.Message.NOT_FOUND_FAILURE, TranslationKey.Menu.Label.MENU));
    }

    @Transactional(readOnly = true)
    public List<MenuInfo> getMenuInfos(List<String> authorityRoles) {
        List<MenuInfo> menus = menuRepository.selectMenuInfos(authorityRoles);

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
        MenuSearch searchForExistingMenu = MenuSearch.ofMenuIdIncludingDeletedData(menuSave.getMenuId());
        Optional<Menu> optionalExistingMenu = menuRepository.selectMenu(searchForExistingMenu);
        if (optionalExistingMenu.isPresent()) {
            Menu existingMenu = optionalExistingMenu.get();
            throw new RestException(
                    existingMenu.isDeleted()
                            ? TranslationKey.Common.Message.DUPLICATE_DELETED_FAILURE
                            : TranslationKey.Common.Message.DUPLICATE_FAILURE,
                    TranslationKey.Menu.Label.MENU,
                    menuSave.getMenuId());
        }

        int createdCount = menuRepository.insertMenu(menuSave);
        if (createdCount == 0) {
            throw new RestException(
                    TranslationKey.Common.Message.CREATE_FAILURE, TranslationKey.Menu.Label.MENU, menuSave.getMenuId());
        }

        MenuSearch menuSearch = MenuSearch.ofMenuId(menuSave.getMenuId());
        Optional<Menu> foundMenu = menuRepository.selectMenu(menuSearch);

        return foundMenu.orElseThrow(() -> new RestException(
                TranslationKey.Common.Message.CREATE_FAILURE, TranslationKey.Menu.Label.MENU, menuSave.getMenuId()));
    }

    @Transactional
    public void updateMenu(MenuUpdate menuUpdate) {
        int updatedMenuCount = menuRepository.updateMenu(menuUpdate);
        if (updatedMenuCount == 0) {
            throw new RestException(
                    TranslationKey.Common.Message.UPDATE_FAILURE,
                    TranslationKey.Menu.Label.MENU,
                    menuUpdate.getTargetMenuId());
        }
    }

    @Transactional
    public void deleteMenu(MenuDelete menuDelete) {
        MenuSearch menuSearch = MenuSearch.ofMenuId(menuDelete.getTargetMenuId());
        menuRepository
                .selectMenu(menuSearch)
                .orElseThrow(() -> new RestException(
                        TranslationKey.Common.Message.NOT_FOUND_FAILURE, TranslationKey.Menu.Label.MENU));

        int deletedCount = menuRepository.deleteMenu(menuDelete);
        if (deletedCount == 0) {
            throw new RestException(
                    TranslationKey.Common.Message.DELETE_FAILURE,
                    TranslationKey.Menu.Label.MENU,
                    menuDelete.getTargetMenuId());
        }
    }
}
