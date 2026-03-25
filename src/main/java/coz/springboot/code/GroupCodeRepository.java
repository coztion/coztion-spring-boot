package coz.springboot.code;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.code.model.GroupCode;
import coz.springboot.code.model.GroupCodeFilter;

@Mapper
public interface GroupCodeRepository {

    List<GroupCode> findGroupCodesByFilter(GroupCodeFilter filter);
}
