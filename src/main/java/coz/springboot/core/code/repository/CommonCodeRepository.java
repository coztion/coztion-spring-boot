package coz.springboot.core.code.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.core.code.dto.CommonCodeSearchParam;
import coz.springboot.core.code.vo.CommonCode;

@Mapper
public interface CommonCodeRepository {

    List<CommonCode> findCommonCodesByParam(CommonCodeSearchParam param);

    Optional<CommonCode> findCommonCodeByParam(CommonCodeSearchParam param);
}
