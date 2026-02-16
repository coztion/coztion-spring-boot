package coz.springboot.service.code;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import coz.springboot.constant.Label;
import coz.springboot.constant.Message;
import coz.springboot.dto.code.CommonCodeSearchParam;
import coz.springboot.exception.ClientException;
import coz.springboot.repository.code.CommonCodeRepository;
import coz.springboot.vo.code.CommonCode;

@Service
@RequiredArgsConstructor
class CommonCodeServiceImpl implements CommonCodeService {

    private final CommonCodeRepository commonCodeRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CommonCode> getCommonCodes(CommonCodeSearchParam command) {
        return commonCodeRepository.findCommonCodesByParam(command);
    }

    @Transactional(readOnly = true)
    @Override
    public CommonCode getCommonCode(CommonCodeSearchParam param) {
        Optional<CommonCode> optionalCommonCode = commonCodeRepository.findCommonCodeByParam(param);
        return optionalCommonCode.orElseThrow(
                () -> new ClientException(Message.Exception.NOT_FOUND, Label.Code.COMMON_CODE));
    }
}
