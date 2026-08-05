package com.coztion.code;

import java.util.Optional;

import com.coztion.code.model.CommonCode;
import com.coztion.code.model.CommonCodeSave;
import com.coztion.code.model.CommonCodeSearch;
import com.coztion.code.model.CommonCodesSearch;
import com.coztion.common.constant.TranslationKey;
import com.coztion.common.exception.RestException;
import com.github.pagehelper.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class CommonCodeService {

    private final CommonCodeRepository commonCodeRepository;

    @Transactional(readOnly = true)
    public Page<CommonCode> getCommonCodes(CommonCodesSearch commonCodesSearch) {
        commonCodesSearch.validatePageable();
        return commonCodeRepository.selectCommonCodes(commonCodesSearch);
    }

    @Transactional
    public CommonCode saveCommonCode(CommonCodeSave commonCodeSave) {
        CommonCodeSearch searchForExistingCommonCode =
                CommonCodeSearch.ofCode(commonCodeSave.getGroupCode(), commonCodeSave.getCommonCode());
        Optional<CommonCode> optionalExistingCommonCode =
                commonCodeRepository.selectCommonCode(searchForExistingCommonCode);
        if (optionalExistingCommonCode.isPresent()) {
            throw new RestException(
                    TranslationKey.Common.Message.DUPLICATE_FAILURE,
                    TranslationKey.Code.Label.COMMON_CODE,
                    commonCodeSave.getCommonCode());
        }

        int createdCount = commonCodeRepository.insertCommonCode(commonCodeSave);
        if (createdCount == 0) {
            throw new RestException(
                    TranslationKey.Common.Message.CREATE_FAILURE,
                    TranslationKey.Code.Label.COMMON_CODE,
                    commonCodeSave.getCommonCode());
        }

        CommonCodeSearch commonCodeSearch =
                CommonCodeSearch.ofCode(commonCodeSave.getGroupCode(), commonCodeSave.getCommonCode());
        Optional<CommonCode> foundCommonCode = commonCodeRepository.selectCommonCode(commonCodeSearch);

        return foundCommonCode.orElseThrow(() -> new RestException(
                TranslationKey.Common.Message.CREATE_FAILURE,
                TranslationKey.Code.Label.COMMON_CODE,
                commonCodeSave.getCommonCode()));
    }
}
