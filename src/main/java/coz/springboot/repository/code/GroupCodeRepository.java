package coz.springboot.repository.code;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.dto.code.GroupCodeSearchParam;
import coz.springboot.vo.code.GroupCode;

@Mapper
public interface GroupCodeRepository {

    List<GroupCode> findGroupCodesByParam(GroupCodeSearchParam param);
}
