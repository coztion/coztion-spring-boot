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
public class GroupCodeOperation {

    @Valid
    private List<GroupCodeSave> groupCodeSaves;

    @Valid
    private List<GroupCodeUpdate> groupCodeUpdates;

    @Valid
    private List<GroupCodeDelete> groupCodeDeletes;

    public boolean hasSaveTargets() {
        return !CollectionUtils.isEmpty(groupCodeSaves);
    }

    public boolean hasUpdateTargets() {
        return !CollectionUtils.isEmpty(groupCodeUpdates);
    }

    public boolean hasDeleteTargets() {
        return !CollectionUtils.isEmpty(groupCodeDeletes);
    }

    public void setAuthor(String author) {
        if (hasSaveTargets()) {
            groupCodeSaves.forEach(groupCodeSave -> groupCodeSave.setAuthor(author));
        }

        if (hasUpdateTargets()) {
            groupCodeUpdates.forEach(groupCodeUpdate -> groupCodeUpdate.setAuthor(author));
        }

        if (hasDeleteTargets()) {
            groupCodeDeletes.forEach(groupCodeDelete -> groupCodeDelete.setAuthor(author));
        }
    }
}
