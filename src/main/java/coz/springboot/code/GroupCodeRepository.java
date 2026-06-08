package coz.springboot.code;

import java.util.Optional;

import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.code.domain.GroupCode;
import coz.springboot.code.domain.GroupCodeCreate;
import coz.springboot.code.domain.GroupCodeDelete;
import coz.springboot.code.domain.GroupCodeFilter;
import coz.springboot.code.domain.GroupCodeUpdate;

@Mapper
public interface GroupCodeRepository {

    Page<GroupCode> findGroupCodesByFilter(GroupCodeFilter filter);

    Optional<GroupCode> findGroupCodeByFilter(GroupCodeFilter filter);

    int insertGroupCode(GroupCodeCreate create);

    int updateGroupCode(GroupCodeUpdate update);

    int updateCommonCodesByGroupCodeUpdate(GroupCodeUpdate update);

    int deleteGroupCode(GroupCodeDelete delete);

    int deleteCommonCodesByGroupCodeDelete(GroupCodeDelete delete);
}
