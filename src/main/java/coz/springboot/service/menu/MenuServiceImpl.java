package coz.springboot.service.menu;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import coz.springboot.domain.menu.Menu;
import coz.springboot.domain.menu.MenuFilter;
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
}
