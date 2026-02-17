package coz.springboot.service.code;

import java.util.List;

import coz.springboot.domain.code.GroupCode;
import coz.springboot.domain.code.GroupCodeFilter;

public interface GroupCodeService {

    List<GroupCode> getGroupCodes(GroupCodeFilter command);
}
