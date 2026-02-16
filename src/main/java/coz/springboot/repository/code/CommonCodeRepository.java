package coz.springboot.repository.code;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.dto.code.CommonCodeSearchParam;
import coz.springboot.vo.code.CommonCode;

@Mapper
public interface CommonCodeRepository {

    List<CommonCode> findCommonCodesByParam(CommonCodeSearchParam param);

    Optional<CommonCode> findCommonCodeByParam(CommonCodeSearchParam param);
}
