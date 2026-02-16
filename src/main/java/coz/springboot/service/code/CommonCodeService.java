package coz.springboot.service.code;

import java.util.List;

import coz.springboot.dto.code.CommonCodeSearchParam;
import coz.springboot.vo.code.CommonCode;

public interface CommonCodeService {

    List<CommonCode> getCommonCodes(CommonCodeSearchParam param);

    CommonCode getCommonCode(CommonCodeSearchParam param);
}
