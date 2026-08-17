package com.coztion.menu.model;

import java.util.List;

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
}
