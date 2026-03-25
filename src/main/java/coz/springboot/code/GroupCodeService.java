package coz.springboot.code;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import coz.springboot.code.domain.GroupCode;
import coz.springboot.code.domain.GroupCodeFilter;

@Service
@RequiredArgsConstructor
public class GroupCodeService {

    private final GroupCodeRepository groupCodeRepository;

    @Transactional(readOnly = true)
    public List<GroupCode> getGroupCodes(GroupCodeFilter filter) {
        return groupCodeRepository.findGroupCodesByFilter(filter);
    }
}
