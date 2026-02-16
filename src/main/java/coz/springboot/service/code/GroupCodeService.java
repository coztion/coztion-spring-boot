package coz.springboot.service.code;

import java.util.List;

import coz.springboot.dto.code.GroupCodeSearchParam;
import coz.springboot.vo.code.GroupCode;

public interface GroupCodeService {

    List<GroupCode> getGroupCodes(GroupCodeSearchParam param);
}
