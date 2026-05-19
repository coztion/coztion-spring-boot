package coz.springboot.code;

import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.code.model.GroupCode;
import coz.springboot.code.model.GroupCodeFilter;

@Mapper
public interface GroupCodeRepository {

    Page<GroupCode> findGroupCodesByFilter(GroupCodeFilter filter);
}
