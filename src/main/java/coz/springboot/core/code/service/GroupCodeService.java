package coz.springboot.core.code.service;

import java.util.List;

import coz.springboot.core.code.dto.GroupCodeSearchParam;
import coz.springboot.core.code.vo.GroupCode;

public interface GroupCodeService {

    List<GroupCode> getGroupCodes(GroupCodeSearchParam param);
}
