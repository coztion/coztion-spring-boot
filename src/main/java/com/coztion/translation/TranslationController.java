package com.coztion.translation;

import java.util.List;

import jakarta.validation.Valid;

import com.coztion.authority.model.AuthorityType;
import com.coztion.common.dto.PageResponse;
import com.coztion.common.dto.RestResponse;
import com.coztion.core.security.Authorize;
import com.coztion.menu.model.MenuId;
import com.coztion.translation.model.Translation;
import com.coztion.translation.model.TranslationDelete;
import com.coztion.translation.model.TranslationInfo;
import com.coztion.translation.model.TranslationOperation;
import com.coztion.translation.model.TranslationSave;
import com.coztion.translation.model.TranslationSearch;
import com.coztion.translation.model.TranslationUpdate;
import com.github.pagehelper.Page;

import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Post Translation")
    @PostMapping
    @Authorize(menuIds = MenuId.TRANSLATION_MANAGEMENT, authorityType = AuthorityType.WRITE)
    public RestResponse<Translation> postTranslation(@Valid @RequestBody TranslationSave translationSave) {
        Translation createdTranslation = translationService.saveTranslation(translationSave);
        return RestResponse.ofSuccess(createdTranslation);
    }

    @Operation(summary = "Put Translation")
    @PutMapping("/{translationCode}")
    @Authorize(menuIds = MenuId.TRANSLATION_MANAGEMENT, authorityType = AuthorityType.WRITE)
    public RestResponse<Translation> putTranslation(@Valid @RequestBody TranslationUpdate translationUpdate) {
        Translation updatedTranslation = translationService.updateTranslation(translationUpdate);
        return RestResponse.ofSuccess(updatedTranslation);
    }

    @Operation(summary = "Delete Translation")
    @DeleteMapping("/{translationCode}")
    @Authorize(menuIds = MenuId.TRANSLATION_MANAGEMENT, authorityType = AuthorityType.WRITE)
    public RestResponse<Boolean> deleteTranslation(@Valid @RequestBody TranslationDelete translationDelete) {
        translationService.deleteTranslation(translationDelete);
        return RestResponse.ofSuccess(true);
    }

    @Operation(summary = "Operate Translations")
    @PostMapping("/operation")
    @Authorize(menuIds = MenuId.TRANSLATION_MANAGEMENT, authorityType = AuthorityType.WRITE)
    public RestResponse<Boolean> operationTranslations(@Valid @RequestBody TranslationOperation translationOperation) {
        translationService.operateTranslations(translationOperation);
        return RestResponse.ofSuccess(true);
    }
}
