package coz.springboot.service.code;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import coz.springboot.domain.code.GroupCode;
import coz.springboot.domain.code.GroupCodeFilter;
import coz.springboot.repository.code.GroupCodeRepository;

@Service
@RequiredArgsConstructor
public class GroupCodeServiceImpl implements GroupCodeService {

    private final GroupCodeRepository groupCodeRepository;

    @Transactional(readOnly = true)
    @Override
    public List<GroupCode> getGroupCodes(GroupCodeFilter filter) {
        return groupCodeRepository.findGroupCodesByFilter(filter);
    }
}
