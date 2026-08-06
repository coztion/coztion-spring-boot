package com.coztion.code;

import java.util.Optional;

import com.coztion.code.model.CommonCode;
import com.coztion.code.model.CommonCodeDelete;
import com.coztion.code.model.CommonCodeSave;
import com.coztion.code.model.CommonCodeSearch;
import com.coztion.code.model.CommonCodeUpdate;
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

    @Transactional(readOnly = true)
    public CommonCode getCommonCode(CommonCodeSearch commonCodeSearch) {
        return commonCodeRepository
                .selectCommonCode(commonCodeSearch)
                .orElseThrow(() -> new RestException(
                        TranslationKey.Common.Message.NOT_FOUND_FAILURE, TranslationKey.Code.Label.COMMON_CODE));
    }

    @Transactional
    public CommonCode saveCommonCode(CommonCodeSave commonCodeSave) {
        CommonCodeSearch searchForExistingCommonCode = CommonCodeSearch.ofCodeIncludingDeletedData(
                commonCodeSave.getGroupCode(), commonCodeSave.getCommonCode());
        Optional<CommonCode> optionalExistingCommonCode =
                commonCodeRepository.selectCommonCode(searchForExistingCommonCode);
        if (optionalExistingCommonCode.isPresent()) {
            CommonCode existingCommonCode = optionalExistingCommonCode.get();
            throw new RestException(
                    existingCommonCode.isDeleted()
                            ? TranslationKey.Common.Message.DUPLICATE_DELETED_FAILURE
                            : TranslationKey.Common.Message.DUPLICATE_FAILURE,
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

    @Transactional
    public CommonCode updateCommonCode(CommonCodeUpdate commonCodeUpdate) {
        CommonCodeSearch commonCodeSearch =
                CommonCodeSearch.ofCode(commonCodeUpdate.getTargetGroupCode(), commonCodeUpdate.getTargetCommonCode());
        commonCodeRepository
                .selectCommonCode(commonCodeSearch)
                .orElseThrow(() -> new RestException(
                        TranslationKey.Common.Message.NOT_FOUND_FAILURE, TranslationKey.Code.Label.COMMON_CODE));

        int updatedCount = commonCodeRepository.updateCommonCode(commonCodeUpdate);
        if (updatedCount == 0) {
            throw new RestException(
                    TranslationKey.Common.Message.UPDATE_FAILURE,
                    TranslationKey.Code.Label.COMMON_CODE,
                    commonCodeUpdate.getTargetCommonCode());
        }

        return commonCodeRepository
                .selectCommonCode(commonCodeSearch)
                .orElseThrow(() -> new RestException(
                        TranslationKey.Common.Message.UPDATE_FAILURE,
                        TranslationKey.Code.Label.COMMON_CODE,
                        commonCodeUpdate.getTargetCommonCode()));
    }

    @Transactional
    public void deleteCommonCode(CommonCodeDelete commonCodeDelete) {
        CommonCodeSearch commonCodeSearch =
                CommonCodeSearch.ofCode(commonCodeDelete.getTargetGroupCode(), commonCodeDelete.getTargetCommonCode());
        commonCodeRepository
                .selectCommonCode(commonCodeSearch)
                .orElseThrow(() -> new RestException(
                        TranslationKey.Common.Message.NOT_FOUND_FAILURE, TranslationKey.Code.Label.COMMON_CODE));

        int deletedCount = commonCodeRepository.deleteCommonCode(commonCodeDelete);
        if (deletedCount == 0) {
            throw new RestException(
                    TranslationKey.Common.Message.DELETE_FAILURE,
                    TranslationKey.Code.Label.COMMON_CODE,
                    commonCodeDelete.getTargetCommonCode());
        }
    }
}
