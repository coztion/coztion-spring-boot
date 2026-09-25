package com.coztion.user.model;

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
public class UserOperation extends OperationAuthor {

    @Valid
    private List<UserSave> userSaves;

    @Valid
    private List<UserUpdate> userUpdates;

    @Valid
    private List<UserDelete> userDeletes;

    public boolean hasSaveTargets() {
        return !CollectionUtils.isEmpty(userSaves);
    }

    public boolean hasUpdateTargets() {
        return !CollectionUtils.isEmpty(userUpdates);
    }

    public boolean hasDeleteTargets() {
        return !CollectionUtils.isEmpty(userDeletes);
    }

    @Override
    public void setAuthor(String author) {
        super.setAuthor(author);

        if (hasSaveTargets()) {
            userSaves.forEach(userSave -> userSave.setAuthor(author));
        }

        if (hasUpdateTargets()) {
            userUpdates.forEach(userUpdate -> userUpdate.setAuthor(author));
        }

        if (hasDeleteTargets()) {
            userDeletes.forEach(userDelete -> userDelete.setAuthor(author));
        }
    }
}
