package coz.springboot.service.code;

import java.util.List;

import coz.springboot.vo.code.CommonCode;
import coz.springboot.vo.code.CommonCodeFilter;

public interface CommonCodeService {

    List<CommonCode> getCommonCodes(CommonCodeFilter filter);

    CommonCode getCommonCode(CommonCodeFilter filter);
}
