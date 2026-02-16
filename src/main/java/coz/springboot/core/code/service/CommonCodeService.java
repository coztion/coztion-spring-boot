package coz.springboot.core.code.service;

import java.util.List;

import coz.springboot.core.code.dto.CommonCodeSearchParam;
import coz.springboot.core.code.vo.CommonCode;

public interface CommonCodeService {

    List<CommonCode> getCommonCodes(CommonCodeSearchParam param);

    CommonCode getCommonCode(CommonCodeSearchParam param);
}
