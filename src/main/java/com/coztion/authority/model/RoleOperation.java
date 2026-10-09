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
public class RoleOperation extends OperationAuthor {

    @Valid
    private List<RoleSave> roleSaves;

    @Valid
    private List<RoleUpdate> roleUpdates;

    @Valid
    private List<RoleDelete> roleDeletes;

    public boolean hasSaveTargets() {
        return !CollectionUtils.isEmpty(roleSaves);
    }

    public boolean hasUpdateTargets() {
        return !CollectionUtils.isEmpty(roleUpdates);
    }

    public boolean hasDeleteTargets() {
        return !CollectionUtils.isEmpty(roleDeletes);
    }

    @Override
    public void setAuthor(String author) {
        super.setAuthor(author);

        if (hasSaveTargets()) {
            roleSaves.forEach(roleSave -> roleSave.setAuthor(author));
        }

        if (hasUpdateTargets()) {
            roleUpdates.forEach(roleUpdate -> roleUpdate.setAuthor(author));
        }

        if (hasDeleteTargets()) {
            roleDeletes.forEach(roleDelete -> roleDelete.setAuthor(author));
        }
    }
}
