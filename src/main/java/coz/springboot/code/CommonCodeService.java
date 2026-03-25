package coz.springboot.code;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import coz.springboot.code.model.CommonCode;
import coz.springboot.code.model.CommonCodeFilter;
import coz.springboot.global.constant.Label;
import coz.springboot.global.constant.Message;
import coz.springboot.global.exception.ClientException;

@Service
@RequiredArgsConstructor
class CommonCodeService {

    private final CommonCodeRepository commonCodeRepository;

    @Transactional(readOnly = true)
    public List<CommonCode> getCommonCodes(CommonCodeFilter filter) {
        return commonCodeRepository.findCommonCodesByFilter(filter);
    }

    @Transactional(readOnly = true)
    public CommonCode getCommonCode(CommonCodeFilter filter) {
        Optional<CommonCode> optionalCommonCode = commonCodeRepository.findCommonCodeByFilter(filter);
        return optionalCommonCode.orElseThrow(
                () -> new ClientException(Message.Exception.NOT_FOUND, Label.Code.COMMON_CODE));
    }
}
