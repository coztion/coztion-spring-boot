package com.coztion.code;

import java.util.Optional;

import com.coztion.code.model.GroupCode;
import com.coztion.code.model.GroupCodeDelete;
import com.coztion.code.model.GroupCodeSave;
import com.coztion.code.model.GroupCodeSearch;
import com.coztion.code.model.GroupCodeUpdate;
import com.coztion.code.model.GroupCodesSearch;
import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GroupCodeRepository {

    Page<GroupCode> selectGroupCodes(GroupCodesSearch groupCodesSearch);

    Optional<GroupCode> selectGroupCode(GroupCodeSearch groupCodeSearch);

    int insertGroupCode(GroupCodeSave groupCodeSave);

    int updateGroupCode(GroupCodeUpdate groupCodeUpdate);

    int updateCommonCodesByGroupCodeUpdate(GroupCodeUpdate groupCodeUpdate);

    int deleteGroupCode(GroupCodeDelete groupCodeDelete);

    int deleteCommonCodesByGroupCodeDelete(GroupCodeDelete groupCodeDelete);
}
