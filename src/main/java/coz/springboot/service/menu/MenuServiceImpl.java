package coz.springboot.service.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import coz.springboot.domain.menu.Menu;
import coz.springboot.domain.menu.MenuFilter;
import coz.springboot.domain.menu.MenuTree;
import coz.springboot.enums.user.Authority;
import coz.springboot.repository.menu.MenuRepository;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Menu> getMenus(MenuFilter filter) {
        return menuRepository.findMenusByFilter(filter);
    }

    @Transactional(readOnly = true)
    @Override
    public List<MenuTree> getMenuTree(List<Authority> authorities) {
        List<MenuTree> menus = menuRepository.findMenuTree(authorities);

        Map<String, MenuTree> map = menus.stream().collect(Collectors.toMap(MenuTree::getMenuId, menu -> menu));

        List<MenuTree> roots = new ArrayList<>();
        for (MenuTree menu : menus) {
            if (menu.getParentMenuId() == null) {
                roots.add(menu);
            } else {
                MenuTree parent = map.get(menu.getParentMenuId());
                if (parent != null) {
                    if (parent.getSubMenus() == null) {
                        parent.setSubMenus(new ArrayList<>());
                    }
                    parent.getSubMenus().add(menu);
                }
            }
        }
        return roots;
    }
}
