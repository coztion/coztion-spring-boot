package coz.springboot.service.code;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import coz.springboot.global.exception.ClientException;
import coz.springboot.global.constant.Label;
import coz.springboot.global.constant.Message;
import coz.springboot.repository.code.CommonCodeRepository;
import coz.springboot.vo.code.CommonCode;
import coz.springboot.vo.code.CommonCodeFilter;

@Service
@RequiredArgsConstructor
class CommonCodeServiceImpl implements CommonCodeService {

    private final CommonCodeRepository commonCodeRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CommonCode> getCommonCodes(CommonCodeFilter filter) {
        return commonCodeRepository.findCommonCodesByFilter(filter);
    }

    @Transactional(readOnly = true)
    @Override
    public CommonCode getCommonCode(CommonCodeFilter filter) {
        Optional<CommonCode> optionalCommonCode = commonCodeRepository.findCommonCodeByFilter(filter);
        return optionalCommonCode.orElseThrow(
                () -> new ClientException(Message.Exception.NOT_FOUND, Label.Code.COMMON_CODE));
    }
}
