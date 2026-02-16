package coz.springboot.core.code.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.core.code.dto.GroupCodeSearchParam;
import coz.springboot.core.code.vo.GroupCode;

@Mapper
public interface GroupCodeRepository {

    List<GroupCode> findGroupCodesByParam(GroupCodeSearchParam param);
}
