package coz.springboot.code;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.code.domain.CommonCode;
import coz.springboot.code.domain.CommonCodeSearch;

@Mapper
public interface CommonCodeRepository {

    List<CommonCode> findCommonCodesByFilter(CommonCodeSearch filter);

    Optional<CommonCode> findCommonCodeByFilter(CommonCodeSearch filter);
}
