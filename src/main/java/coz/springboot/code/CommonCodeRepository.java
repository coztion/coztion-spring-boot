package coz.springboot.code;

import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.code.domain.CommonCode;
import coz.springboot.code.domain.CommonCodeSearch;

@Mapper
public interface CommonCodeRepository {

    Page<CommonCode> findCommonCodes(CommonCodeSearch filter);
}
