package coz.springboot.service.code;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import coz.springboot.constant.Label;
import coz.springboot.constant.Message;
import coz.springboot.domain.code.CommonCode;
import coz.springboot.domain.code.CommonCodeFilter;
import coz.springboot.exception.ClientException;
import coz.springboot.repository.code.CommonCodeRepository;

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
