package coz.springboot.repository.code;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.vo.code.GroupCode;
import coz.springboot.vo.code.GroupCodeFilter;

@Mapper
public interface GroupCodeRepository {

    List<GroupCode> findGroupCodesByFilter(GroupCodeFilter filter);
}
