package coz.springboot.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.github.pagehelper.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import lombok.RequiredArgsConstructor;

import coz.springboot.common.constant.TranslationKey;
import coz.springboot.common.exception.RestException;
import coz.springboot.menu.model.*;
import coz.springboot.user.model.Authority;

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
            List<MenuId> menuIds = menus.stream().map(Menu::getMenuId).toList();
            List<MenuAuthority> menuAuthorities =
                    menuAuthorityRepository.selectMenuAuthorities(MenuAuthoritiesSearch.ofMenuIds(menuIds));
            Map<MenuId, List<Authority>> menuIdMenuAuthorities = menuAuthorities.stream()
                    .collect(Collectors.groupingBy(
                            MenuAuthority::getMenuId,
                            Collectors.mapping(MenuAuthority::getAuthority, Collectors.toList())));
            menus.forEach(menu -> menu.setAuthorities(menuIdMenuAuthorities.getOrDefault(menu.getMenuId(), List.of())));
        }

        return menus;
    }

    @Transactional(readOnly = true)
    public List<MenuInfo> getMenuInfos(List<Authority> authorities) {
        List<MenuInfo> menus = menuRepository.selectMenuInfos(authorities);

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
    public void updateMenu(MenuUpdate menuUpdate) {
        int updatedMenuCount = menuRepository.updateMenu(menuUpdate);
        if (updatedMenuCount == 0) {
            throw new RestException(
                    TranslationKey.Common.Message.UPDATE_FAILURE,
                    TranslationKey.Menu.Label.MENU,
                    menuUpdate.getTargetMenuId());
        }
    }
}
