package coz.springboot.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.github.pagehelper.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import coz.springboot.common.constant.Label;
import coz.springboot.common.constant.Message;
import coz.springboot.common.exception.RestException;
import coz.springboot.menu.domain.Menu;
import coz.springboot.menu.domain.MenuInfo;
import coz.springboot.menu.domain.MenuUpdate;
import coz.springboot.menu.domain.MenusSearch;
import coz.springboot.user.domain.Authority;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional(readOnly = true)
    public Page<Menu> getMenus(MenusSearch menusSearch) {
        menusSearch.validatePageable();
        return menuRepository.selectMenus(menusSearch);
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
    public void updateMenu(MenuUpdate update) {
        int updatedMenuCount = menuRepository.updateMenu(update);
        if (updatedMenuCount == 0) {
            throw new RestException(Message.Exception.UPDATE_FAILURE, Label.Menu.MENU);
        }
    }
}
