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

import coz.springboot.menu.model.Menu;
import coz.springboot.menu.model.MenuFilter;
import coz.springboot.menu.model.MenuTree;
import coz.springboot.user.model.Authority;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional(readOnly = true)
    public Page<Menu> getMenus(MenuFilter filter) {
        filter.validatePageable();
        return menuRepository.findMenusByFilter(filter);
    }

    @Transactional(readOnly = true)
    public List<MenuTree> getMenuTree(List<Authority> authorities) {
        List<MenuTree> menus = menuRepository.findMenuTree(authorities);

        Map<String, MenuTree> map = menus.stream().collect(Collectors.toMap(MenuTree::getMenuId, menu -> menu));

        List<MenuTree> roots = new ArrayList<>();
        for (MenuTree menu : menus) {
            if (Objects.isNull(menu.getParentMenuId())) {
                roots.add(menu);
            } else {
                MenuTree parent = map.get(menu.getParentMenuId());
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
}
