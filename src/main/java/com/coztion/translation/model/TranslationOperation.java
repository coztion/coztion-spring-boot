package com.coztion.translation.model;

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
public class TranslationOperation extends OperationAuthor {

    @Valid
    private List<TranslationSave> translationSaves;

    @Valid
    private List<TranslationUpdate> translationUpdates;

    @Valid
    private List<TranslationDelete> translationDeletes;

    public boolean hasSaveTargets() {
        return !CollectionUtils.isEmpty(translationSaves);
    }

    public boolean hasUpdateTargets() {
        return !CollectionUtils.isEmpty(translationUpdates);
    }

    public boolean hasDeleteTargets() {
        return !CollectionUtils.isEmpty(translationDeletes);
    }

    @Override
    public void setAuthor(String author) {
        super.setAuthor(author);

        if (hasSaveTargets()) {
            translationSaves.forEach(translationSave -> translationSave.setAuthor(author));
        }

        if (hasUpdateTargets()) {
            translationUpdates.forEach(translationUpdate -> translationUpdate.setAuthor(author));
        }

        if (hasDeleteTargets()) {
            translationDeletes.forEach(translationDelete -> translationDelete.setAuthor(author));
        }
    }
}
