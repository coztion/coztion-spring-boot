package coz.springboot.core.code.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import coz.springboot.core.code.dto.GroupCodeSearchParam;
import coz.springboot.core.code.repository.GroupCodeRepository;
import coz.springboot.core.code.vo.GroupCode;

@Service
@RequiredArgsConstructor
public class GroupCodeServiceImpl implements GroupCodeService {

    private final GroupCodeRepository groupCodeRepository;

    @Transactional(readOnly = true)
    @Override
    public List<GroupCode> getGroupCodes(GroupCodeSearchParam param) {
        return groupCodeRepository.findGroupCodesByParam(param);
    }
}
