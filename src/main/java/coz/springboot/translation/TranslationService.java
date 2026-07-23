package coz.springboot.translation;

import com.github.pagehelper.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import coz.springboot.translation.model.Translation;
import coz.springboot.translation.model.TranslationSearch;

@Service
@RequiredArgsConstructor
public class TranslationService {

    private final TranslationRepository translationRepository;

    @Transactional(readOnly = true)
    public Page<Translation> getTranslations(TranslationSearch translationSearch) {
        translationSearch.validatePageable();
        return translationRepository.selectTranslations(translationSearch);
    }
}
