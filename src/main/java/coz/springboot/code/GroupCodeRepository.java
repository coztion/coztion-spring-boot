package coz.springboot.code;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.code.domain.GroupCode;
import coz.springboot.code.domain.GroupCodeFilter;

@Mapper
public interface GroupCodeRepository {

    List<GroupCode> findGroupCodesByFilter(GroupCodeFilter filter);
}
