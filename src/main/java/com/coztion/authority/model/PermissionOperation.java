package com.coztion.authority.model;

import java.util.List;

import jakarta.validation.Valid;

import com.coztion.common.model.OperationAuthor;

import org.springframework.util.CollectionUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PermissionOperation extends OperationAuthor {

    @Valid
    private List<PermissionSave> permissionSaves;

    @Valid
    private List<PermissionUpdate> permissionUpdates;

    @Valid
    private List<PermissionDelete> permissionDeletes;

    public boolean hasSaveTargets() {
        return !CollectionUtils.isEmpty(permissionSaves);
    }

    public boolean hasUpdateTargets() {
        return !CollectionUtils.isEmpty(permissionUpdates);
    }

    public boolean hasDeleteTargets() {
        return !CollectionUtils.isEmpty(permissionDeletes);
    }

    @Override
    public void setAuthor(String author) {
        super.setAuthor(author);

        if (hasSaveTargets()) {
            permissionSaves.forEach(permissionSave -> permissionSave.setAuthor(author));
        }

        if (hasUpdateTargets()) {
            permissionUpdates.forEach(permissionUpdate -> permissionUpdate.setAuthor(author));
        }

        if (hasDeleteTargets()) {
            permissionDeletes.forEach(permissionDelete -> permissionDelete.setAuthor(author));
        }
    }
}
