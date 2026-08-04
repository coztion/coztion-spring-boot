package com.coztion.translation;

import com.coztion.translation.model.Translation;
import com.coztion.translation.model.TranslationSearch;
import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TranslationRepository {

    Page<Translation> selectTranslations(TranslationSearch translationSearch);
}
