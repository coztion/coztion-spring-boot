package coz.springboot.service.code;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import coz.springboot.dto.code.GroupCodeSearchParam;
import coz.springboot.repository.code.GroupCodeRepository;
import coz.springboot.vo.code.GroupCode;

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
