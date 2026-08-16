package com.coztion.code;

import java.util.Optional;

import com.coztion.code.model.GroupCode;
import com.coztion.code.model.GroupCodeDelete;
import com.coztion.code.model.GroupCodeSave;
import com.coztion.code.model.GroupCodeSearch;
import com.coztion.code.model.GroupCodeUpdate;
import com.coztion.code.model.GroupCodesSearch;
import com.coztion.common.constant.TranslationKey;
import com.coztion.common.exception.RestException;
import com.github.pagehelper.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupCodeService {

    private final GroupCodeRepository groupCodeRepository;

    @Transactional(readOnly = true)
    public Page<GroupCode> getGroupCodes(GroupCodesSearch groupCodesSearch) {
        groupCodesSearch.validatePageable();
        return groupCodeRepository.selectGroupCodes(groupCodesSearch);
    }

    @Transactional(readOnly = true)
    public GroupCode getGroupCode(GroupCodeSearch groupCodeSearch) {
        return groupCodeRepository
                .selectGroupCode(groupCodeSearch)
                .orElseThrow(() -> new RestException(
                        TranslationKey.Common.Message.NOT_FOUND_FAILURE, TranslationKey.Code.Label.GROUP_CODE));
    }

    @Transactional
    public GroupCode saveGroupCode(GroupCodeSave groupCodeSave) {
        GroupCodeSearch searchForExistingGroupCode =
                GroupCodeSearch.ofGroupCodeIncludingDeletedData(groupCodeSave.getGroupCode());
        Optional<GroupCode> optionalExistingGroupCode = groupCodeRepository.selectGroupCode(searchForExistingGroupCode);
        if (optionalExistingGroupCode.isPresent()) {
            GroupCode existingGroupCode = optionalExistingGroupCode.get();
            throw new RestException(
                    existingGroupCode.isDeleted()
                            ? TranslationKey.Common.Message.DUPLICATE_DELETED_FAILURE
                            : TranslationKey.Common.Message.DUPLICATE_FAILURE,
                    TranslationKey.Code.Label.GROUP_CODE,
                    groupCodeSave.getGroupCode());
        }

        int createdCount = groupCodeRepository.insertGroupCode(groupCodeSave);
        if (createdCount == 0) {
            throw new RestException(TranslationKey.Common.Message.CREATE_FAILURE);
        }

        GroupCodeSearch groupCodeSearch = GroupCodeSearch.ofGroupCode(groupCodeSave.getGroupCode());
        Optional<GroupCode> foundGroupCode = groupCodeRepository.selectGroupCode(groupCodeSearch);

        return foundGroupCode.orElseThrow(() -> new RestException(TranslationKey.Common.Message.CREATE_FAILURE));
    }

    @Transactional
    public GroupCode updateGroupCode(GroupCodeUpdate groupCodeUpdate) {
        GroupCodeSearch groupCodeSearch = GroupCodeSearch.ofGroupCode(groupCodeUpdate.getTargetGroupCode());
        GroupCode groupCode = groupCodeRepository
                .selectGroupCode(groupCodeSearch)
                .orElseThrow(() -> new RestException(
                        TranslationKey.Common.Message.NOT_FOUND_FAILURE, TranslationKey.Code.Label.GROUP_CODE));

        int updatedCount = groupCodeRepository.updateGroupCode(groupCodeUpdate);
        if (updatedCount == 0) {
            throw new RestException(TranslationKey.Common.Message.UPDATE_FAILURE);
        }

        if (groupCodeUpdate.hasDataStatusChanged(groupCode)) {
            groupCodeRepository.updateCommonCodesByGroupCodeUpdate(groupCodeUpdate);
        }

        return groupCodeRepository
                .selectGroupCode(groupCodeSearch)
                .orElseThrow(() -> new RestException(TranslationKey.Common.Message.UPDATE_FAILURE));
    }

    @Transactional
    public void deleteGroupCode(GroupCodeDelete groupCodeDelete) {
        GroupCodeSearch groupCodeSearch = GroupCodeSearch.ofGroupCode(groupCodeDelete.getTargetGroupCode());
        GroupCode groupCode = groupCodeRepository
                .selectGroupCode(groupCodeSearch)
                .orElseThrow(() -> new RestException(TranslationKey.Common.Message.NOT_FOUND_FAILURE));

        if (groupCode.hasEnabledCommonCodes()) {
            throw new RestException(TranslationKey.Code.Message.GROUP_CODE_DELETE_FAILURE_BY_ENABLED_COMMON_CODE);
        }

        if (groupCode.hasCommonCodes()) {
            groupCodeRepository.deleteCommonCodesByGroupCodeDelete(groupCodeDelete);
        }

        int deletedCount = groupCodeRepository.deleteGroupCode(groupCodeDelete);
        if (deletedCount == 0) {
            throw new RestException(TranslationKey.Common.Message.DELETE_FAILURE);
        }
    }
}
