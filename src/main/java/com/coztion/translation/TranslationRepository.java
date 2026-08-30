package com.coztion.translation;

import java.util.Optional;

import com.coztion.translation.model.Translation;
import com.coztion.translation.model.TranslationDelete;
import com.coztion.translation.model.TranslationSave;
import com.coztion.translation.model.TranslationSearch;
import com.coztion.translation.model.TranslationUpdate;
import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TranslationRepository {

    Page<Translation> selectTranslations(TranslationSearch translationSearch);

    Optional<Translation> selectTranslation(TranslationSearch translationSearch);

    int insertTranslation(TranslationSave translationSave);

    int updateTranslation(TranslationUpdate translationUpdate);

    int deleteTranslation(TranslationDelete translationDelete);
}
