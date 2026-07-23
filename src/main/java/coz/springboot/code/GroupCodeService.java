package coz.springboot.code;

import java.util.Optional;

import com.github.pagehelper.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import coz.springboot.code.model.GroupCode;
import coz.springboot.code.model.GroupCodeDelete;
import coz.springboot.code.model.GroupCodeSave;
import coz.springboot.code.model.GroupCodeSearch;
import coz.springboot.code.model.GroupCodeUpdate;
import coz.springboot.code.model.GroupCodesSearch;
import coz.springboot.common.constant.Message;
import coz.springboot.common.exception.RestException;

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
                .orElseThrow(() -> new RestException(Message.Common.Failure.NOT_FOUND_FAILURE));
    }

    @Transactional
    public GroupCode saveGroupCode(GroupCodeSave groupCodeSave) {
        GroupCodeSearch searchForExistingGroupCode = GroupCodeSearch.ofGroupCode(groupCodeSave.getGroupCode());
        Optional<GroupCode> optionalExistingGroupCode = groupCodeRepository.selectGroupCode(searchForExistingGroupCode);
        if (optionalExistingGroupCode.isPresent()) {
            throw new RestException(Message.Common.Failure.DUPLICATE_FAILURE);
        }

        int createdCount = groupCodeRepository.insertGroupCode(groupCodeSave);
        if (createdCount == 0) {
            throw new RestException(Message.Common.Failure.SAVE_FAILURE);
        }

        GroupCodeSearch groupCodeSearch = GroupCodeSearch.ofGroupCode(groupCodeSave.getGroupCode());
        Optional<GroupCode> foundGroupCode = groupCodeRepository.selectGroupCode(groupCodeSearch);

        return foundGroupCode.orElseThrow(() -> new RestException(Message.Common.Failure.SAVE_FAILURE));
    }

    @Transactional
    public GroupCode updateGroupCode(GroupCodeUpdate groupCodeUpdate) {
        GroupCodeSearch groupCodeSearch = GroupCodeSearch.ofGroupCode(groupCodeUpdate.getTargetGroupCode());
        GroupCode groupCode = groupCodeRepository
                .selectGroupCode(groupCodeSearch)
                .orElseThrow(() -> new RestException(Message.Common.Failure.NOT_FOUND_FAILURE));

        int updatedCount = groupCodeRepository.updateGroupCode(groupCodeUpdate);
        if (updatedCount == 0) {
            throw new RestException(Message.Common.Failure.UPDATE_FAILURE);
        }

        if (groupCodeUpdate.hasDataStatusChanged(groupCode)) {
            groupCodeRepository.updateCommonCodesByGroupCodeUpdate(groupCodeUpdate);
        }

        return groupCodeRepository
                .selectGroupCode(groupCodeSearch)
                .orElseThrow(() -> new RestException(Message.Common.Failure.UPDATE_FAILURE));
    }

    @Transactional
    public void deleteGroupCode(GroupCodeDelete groupCodeDelete) {
        GroupCodeSearch groupCodeSearch = GroupCodeSearch.ofGroupCode(groupCodeDelete.getTargetGroupCode());
        GroupCode groupCode = groupCodeRepository
                .selectGroupCode(groupCodeSearch)
                .orElseThrow(() -> new RestException(Message.Common.Failure.NOT_FOUND_FAILURE));

        if (groupCode.hasEnabledCommonCodes()) {
            throw new RestException(Message.Code.Failure.GROUP_CODE_DELETE_FAILURE_BY_ENABLED_COMMON_CODE);
        }

        if (groupCode.hasCommonCodes()) {
            groupCodeRepository.deleteCommonCodesByGroupCodeDelete(groupCodeDelete);
        }

        int deletedCount = groupCodeRepository.deleteGroupCode(groupCodeDelete);
        if (deletedCount == 0) {
            throw new RestException(Message.Common.Failure.DELETE_FAILURE);
        }
    }
}
