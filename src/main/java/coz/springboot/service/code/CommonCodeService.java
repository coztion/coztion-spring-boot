package coz.springboot.service.code;

import java.util.List;

import coz.springboot.domain.code.CommonCode;
import coz.springboot.domain.code.CommonCodeFilter;

public interface CommonCodeService {

    List<CommonCode> getCommonCodes(CommonCodeFilter filter);

    CommonCode getCommonCode(CommonCodeFilter filter);
}
