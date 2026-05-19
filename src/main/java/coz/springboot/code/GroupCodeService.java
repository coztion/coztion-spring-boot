package coz.springboot.code;

import com.github.pagehelper.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import coz.springboot.code.model.GroupCode;
import coz.springboot.code.model.GroupCodeFilter;

@Service
@RequiredArgsConstructor
public class GroupCodeService {

    private final GroupCodeRepository groupCodeRepository;

    @Transactional(readOnly = true)
    public Page<GroupCode> getGroupCodes(GroupCodeFilter filter) {
        filter.validatePageable();
        return groupCodeRepository.findGroupCodesByFilter(filter);
    }
}
