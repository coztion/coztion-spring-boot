package coz.springboot.translation;

import com.github.pagehelper.Page;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import coz.springboot.common.dto.PageResponse;
import coz.springboot.common.dto.RestResponse;
import coz.springboot.translation.model.Translation;
import coz.springboot.translation.model.TranslationSearch;

@Tag(name = "Translation", description = "Translation API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/translations")
public class TranslationController {

    private final TranslationService translationService;

    @Operation(summary = "Get Translations")
    @GetMapping
    public RestResponse<PageResponse<Translation>> getTranslations(
            @ModelAttribute TranslationSearch translationSearch) {
        Page<Translation> translations = translationService.getTranslations(translationSearch);
        return RestResponse.ofSuccess(PageResponse.ofPage(translations));
    }
}
