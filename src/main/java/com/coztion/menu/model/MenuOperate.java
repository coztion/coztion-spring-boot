package com.coztion.menu.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.util.CollectionUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuOperate {

    @Valid
    private List<MenuSave> menuSaves;

    @Valid
    private List<MenuUpdate> menuUpdates;

    @Valid
    private List<MenuDelete> menuDeletes;

    public boolean hasSaveTargets() {
        return !CollectionUtils.isEmpty(menuSaves);
    }

    public boolean hasUpdateTargets() {
        return !CollectionUtils.isEmpty(menuUpdates);
    }

    public boolean hasDeleteTargets() {
        return !CollectionUtils.isEmpty(menuDeletes);
    }

    public List<String> getDeleteMenuIds() {
        return menuDeletes.stream().map(MenuDelete::getMenuId).collect(Collectors.toList());
    }

    public List<MenuSave> getMenuSavesSortedByHierarchy() {
        Map<String, String> parentMenuIdByMenuId = menuSaves.stream()
                .collect(
                        HashMap::new,
                        (map, menuSave) -> map.put(menuSave.getMenuId(), menuSave.getParentMenuId()),
                        HashMap::putAll);

        return menuSaves.stream()
                .sorted(Comparator.comparingInt(
                        menuSave -> depthWithinBatch(menuSave.getMenuId(), parentMenuIdByMenuId)))
                .toList();
    }

    public List<MenuDelete> getMenuDeletesSortedByHierarchy(Map<String, String> parentMenuIdsOfDeleteMenus) {
        return menuDeletes.stream()
                .sorted(Comparator.comparingInt((MenuDelete menuDelete) ->
                                depthWithinBatch(menuDelete.getMenuId(), parentMenuIdsOfDeleteMenus))
                        .reversed())
                .toList();
    }

    public void setAuthor(String author) {
        if (hasSaveTargets()) {
            menuSaves.forEach(menuSave -> menuSave.setAuthor(author));
        }

        if (hasUpdateTargets()) {
            menuUpdates.forEach(menuUpdate -> menuUpdate.setAuthor(author));
        }

        if (hasDeleteTargets()) {
            menuDeletes.forEach(menuDelete -> menuDelete.setAuthor(author));
        }
    }

    private int depthWithinBatch(String menuId, Map<String, String> parentMenuIdByMenuId) {
        int depth = 0;
        String parentMenuId = parentMenuIdByMenuId.get(menuId);
        while (parentMenuId != null && parentMenuIdByMenuId.containsKey(parentMenuId)) {
            depth++;
            parentMenuId = parentMenuIdByMenuId.get(parentMenuId);
        }
        return depth;
    }
}
