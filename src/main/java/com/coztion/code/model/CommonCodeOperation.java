package com.coztion.code.model;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.util.CollectionUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonCodeOperation {

    @Valid
    private List<CommonCodeSave> commonCodeSaves;

    @Valid
    private List<CommonCodeUpdate> commonCodeUpdates;

    @Valid
    private List<CommonCodeDelete> commonCodeDeletes;

    public boolean hasSaveTargets() {
        return !CollectionUtils.isEmpty(commonCodeSaves);
    }

    public boolean hasUpdateTargets() {
        return !CollectionUtils.isEmpty(commonCodeUpdates);
    }

    public boolean hasDeleteTargets() {
        return !CollectionUtils.isEmpty(commonCodeDeletes);
    }

    public void setAuthor(String author) {
        if (hasSaveTargets()) {
            commonCodeSaves.forEach(commonCodeSave -> commonCodeSave.setAuthor(author));
        }

        if (hasUpdateTargets()) {
            commonCodeUpdates.forEach(commonCodeUpdate -> commonCodeUpdate.setAuthor(author));
        }

        if (hasDeleteTargets()) {
            commonCodeDeletes.forEach(commonCodeDelete -> commonCodeDelete.setAuthor(author));
        }
    }
}
