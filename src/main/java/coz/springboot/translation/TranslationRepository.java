package coz.springboot.translation;

import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.translation.model.Translation;
import coz.springboot.translation.model.TranslationSearch;

@Mapper
public interface TranslationRepository {

    Page<Translation> selectTranslations(TranslationSearch translationSearch);
}
