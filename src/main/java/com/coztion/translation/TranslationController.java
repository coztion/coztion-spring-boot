package com.coztion.translation;

import java.util.List;

import com.coztion.common.dto.PageResponse;
import com.coztion.common.dto.RestResponse;
import com.coztion.core.security.Authorize;
import com.coztion.menu.model.MenuId;
import com.coztion.translation.model.Translation;
import com.coztion.translation.model.TranslationInfo;
import com.coztion.translation.model.TranslationSearch;
import com.github.pagehelper.Page;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

@Tag(name = "Translation", description = "Translation API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/translations")
public class TranslationController {

    private final TranslationService translationService;

    @Operation(summary = "Get Translations")
    @GetMapping
    @Authorize(menuIds = MenuId.TRANSLATION_MANAGEMENT)
    public RestResponse<PageResponse<Translation>> getTranslations(
            @ModelAttribute TranslationSearch translationSearch) {
        Page<Translation> translations = translationService.getTranslations(translationSearch);
        return RestResponse.ofSuccess(PageResponse.ofPage(translations));
    }

    @Operation(summary = "Get Translation Information")
    @GetMapping("/infos")
    public RestResponse<List<TranslationInfo>> getTranslationInfos() {
        List<TranslationInfo> translationInfos = translationService.getTranslationInfos();
        return RestResponse.ofSuccess(translationInfos);
    }
}
