package coz.springboot.code;

import java.util.Optional;

import com.github.pagehelper.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import coz.springboot.code.domain.GroupCode;
import coz.springboot.code.domain.GroupCodeCreate;
import coz.springboot.code.domain.GroupCodeDelete;
import coz.springboot.code.domain.GroupCodeSearch;
import coz.springboot.code.domain.GroupCodeUpdate;
import coz.springboot.common.constant.Label;
import coz.springboot.common.constant.Message;
import coz.springboot.common.exception.RestException;

@Service
@RequiredArgsConstructor
public class GroupCodeService {

    private final GroupCodeRepository groupCodeRepository;

    @Transactional(readOnly = true)
    public Page<GroupCode> searchGroupCodes(GroupCodeSearch filter) {
        filter.validatePageable();
        return groupCodeRepository.findGroupCodesByFilter(filter);
    }

    @Transactional
    public GroupCode saveGroupCode(GroupCodeCreate groupCodeCreate) {
        int createdCount = groupCodeRepository.insertGroupCode(groupCodeCreate);
        if (createdCount == 0) {
            throw new RestException(Message.Exception.CREATE_FAILURE, Label.Code.GROUP_CODE);
        }

        GroupCodeSearch groupCodeSearch = GroupCodeSearch.ofGroupCode(groupCodeCreate.getGroupCode());
        Optional<GroupCode> foundGroupCode = groupCodeRepository.findGroupCodeByFilter(groupCodeSearch);

        return foundGroupCode.orElseThrow(
                () -> new RestException(Message.Exception.CREATE_FAILURE, Label.Code.GROUP_CODE));
    }

    @Transactional
    public GroupCode updateGroupCode(GroupCodeUpdate groupCodeUpdate) {
        GroupCodeSearch groupCodeSearch = GroupCodeSearch.ofGroupCode(groupCodeUpdate.getTargetGroupCode());
        GroupCode groupCode = groupCodeRepository
                .findGroupCodeByFilter(groupCodeSearch)
                .orElseThrow(() -> new RestException(Message.Exception.NOT_FOUND, Label.Code.GROUP_CODE));

        int updatedCount = groupCodeRepository.updateGroupCode(groupCodeUpdate);
        if (updatedCount == 0) {
            throw new RestException(Message.Exception.UPDATE_FAILURE, Label.Code.GROUP_CODE);
        }

        if (groupCodeUpdate.hasUsageChanged(groupCode)) {
            groupCodeRepository.updateCommonCodesByGroupCodeUpdate(groupCodeUpdate);
        }

        return groupCodeRepository
                .findGroupCodeByFilter(groupCodeSearch)
                .orElseThrow(() -> new RestException(Message.Exception.UPDATE_FAILURE, Label.Code.GROUP_CODE));
    }

    @Transactional
    public void deleteGroupCode(GroupCodeDelete groupCodeDelete) {
        GroupCodeSearch groupCodeSearch = GroupCodeSearch.ofGroupCode(groupCodeDelete.getTargetGroupCode());
        GroupCode groupCode = groupCodeRepository
                .findGroupCodeByFilter(groupCodeSearch)
                .orElseThrow(() -> new RestException(Message.Exception.NOT_FOUND, Label.Code.GROUP_CODE));

        if (groupCode.hasCommonCodes()) {
            groupCodeRepository.deleteCommonCodesByGroupCodeDelete(groupCodeDelete);
        }

        int deletedCount = groupCodeRepository.deleteGroupCode(groupCodeDelete);
        if (deletedCount == 0) {
            throw new RestException(Message.Exception.DELETE_FAILURE, Label.Code.GROUP_CODE);
        }
    }
}
