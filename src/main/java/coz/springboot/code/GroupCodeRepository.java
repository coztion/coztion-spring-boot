package coz.springboot.code;

import java.util.Optional;

import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.code.model.GroupCode;
import coz.springboot.code.model.GroupCodeDelete;
import coz.springboot.code.model.GroupCodeSave;
import coz.springboot.code.model.GroupCodeSearch;
import coz.springboot.code.model.GroupCodeUpdate;
import coz.springboot.code.model.GroupCodesSearch;

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
