package coz.springboot.service.code;

import java.util.List;

import coz.springboot.vo.code.GroupCode;
import coz.springboot.vo.code.GroupCodeFilter;

public interface GroupCodeService {

    List<GroupCode> getGroupCodes(GroupCodeFilter filter);
}
