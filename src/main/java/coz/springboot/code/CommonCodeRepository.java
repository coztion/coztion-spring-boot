package coz.springboot.code;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.code.model.CommonCode;
import coz.springboot.code.model.CommonCodeFilter;

@Mapper
public interface CommonCodeRepository {

    List<CommonCode> findCommonCodesByFilter(CommonCodeFilter filter);

    Optional<CommonCode> findCommonCodeByFilter(CommonCodeFilter filter);
}
