package coz.springboot.repository.code;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.domain.code.GroupCode;
import coz.springboot.domain.code.GroupCodeFilter;

@Mapper
public interface GroupCodeRepository {

    List<GroupCode> findGroupCodesByFilter(GroupCodeFilter filter);
}
