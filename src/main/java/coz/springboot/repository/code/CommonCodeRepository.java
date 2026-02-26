package coz.springboot.repository.code;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.domain.code.CommonCode;
import coz.springboot.domain.code.CommonCodeFilter;

@Mapper
public interface CommonCodeRepository {

    List<CommonCode> findCommonCodesByFilter(CommonCodeFilter filter);

    Optional<CommonCode> findCommonCodeByFilter(CommonCodeFilter filter);
}
