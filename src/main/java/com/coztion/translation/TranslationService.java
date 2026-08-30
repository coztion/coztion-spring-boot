package com.coztion.translation;

import java.util.List;
import java.util.Optional;

import com.coztion.common.constant.TranslationKey;
import com.coztion.common.exception.RestException;
import com.coztion.translation.model.Translation;
import com.coztion.translation.model.TranslationDelete;
import com.coztion.translation.model.TranslationInfo;
import com.coztion.translation.model.TranslationOperation;
import com.coztion.translation.model.TranslationSave;
import com.coztion.translation.model.TranslationSearch;
import com.coztion.translation.model.TranslationUpdate;
import com.github.pagehelper.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TranslationService {

    private final TranslationRepository translationRepository;

    @Transactional(readOnly = true)
    public Page<Translation> getTranslations(TranslationSearch translationSearch) {
        translationSearch.validatePageable();
        return translationRepository.selectTranslations(translationSearch);
    }

    @Transactional(readOnly = true)
    public List<TranslationInfo> getTranslationInfos() {
        TranslationSearch translationSearch = TranslationSearch.ofEnabled();
        List<Translation> translations = translationRepository.selectTranslations(translationSearch);
        return translations.stream().map(Translation::toTranslationInfo).toList();
    }

    @Transactional
    public Translation saveTranslation(TranslationSave translationSave) {
        TranslationSearch translationSearch = TranslationSearch.ofKey(
                translationSave.getTranslationGroup(),
                translationSave.getTranslationType(),
                translationSave.getTranslationCode(),
                translationSave.getTranslationLocale());
        Optional<Translation> optionalExistingTranslation = translationRepository.selectTranslation(translationSearch);
        if (optionalExistingTranslation.isPresent()) {
            throw new RestException(
                    TranslationKey.Common.Message.DUPLICATE_FAILURE,
                    TranslationKey.Translation.Label.TRANSLATION_CODE,
                    translationSave.getTranslationCode());
        }

        int createdCount = translationRepository.insertTranslation(translationSave);
        if (createdCount == 0) {
            throw new RestException(TranslationKey.Common.Message.CREATE_FAILURE);
        }

        return translationRepository
                .selectTranslation(translationSearch)
                .orElseThrow(() -> new RestException(TranslationKey.Common.Message.CREATE_FAILURE));
    }

    @Transactional
    public Translation updateTranslation(TranslationUpdate translationUpdate) {
        TranslationSearch translationSearch = TranslationSearch.ofKey(
                translationUpdate.getTranslationGroup(),
                translationUpdate.getTranslationType(),
                translationUpdate.getTranslationCode(),
                translationUpdate.getTranslationLocale());
        translationRepository
                .selectTranslation(translationSearch)
                .orElseThrow(() -> new RestException(
                        TranslationKey.Common.Message.NOT_FOUND_FAILURE,
                        TranslationKey.Translation.Label.TRANSLATION_CODE));

        int updatedCount = translationRepository.updateTranslation(translationUpdate);
        if (updatedCount == 0) {
            throw new RestException(TranslationKey.Common.Message.UPDATE_FAILURE);
        }

        return translationRepository
                .selectTranslation(translationSearch)
                .orElseThrow(() -> new RestException(TranslationKey.Common.Message.UPDATE_FAILURE));
    }

    @Transactional
    public void deleteTranslation(TranslationDelete translationDelete) {
        TranslationSearch translationSearch = TranslationSearch.ofKey(
                translationDelete.getTranslationGroup(),
                translationDelete.getTranslationType(),
                translationDelete.getTranslationCode(),
                translationDelete.getTranslationLocale());
        translationRepository
                .selectTranslation(translationSearch)
                .orElseThrow(() -> new RestException(
                        TranslationKey.Common.Message.NOT_FOUND_FAILURE,
                        TranslationKey.Translation.Label.TRANSLATION_CODE));

        int deletedCount = translationRepository.deleteTranslation(translationDelete);
        if (deletedCount == 0) {
            throw new RestException(TranslationKey.Common.Message.DELETE_FAILURE);
        }
    }

    @Transactional
    public void operateTranslations(TranslationOperation translationOperation) {
        if (translationOperation.hasDeleteTargets()) {
            translationOperation.getTranslationDeletes().forEach(this::deleteTranslation);
        }

        if (translationOperation.hasUpdateTargets()) {
            translationOperation.getTranslationUpdates().forEach(this::updateTranslation);
        }

        if (translationOperation.hasSaveTargets()) {
            translationOperation.getTranslationSaves().forEach(this::saveTranslation);
        }
    }
}
