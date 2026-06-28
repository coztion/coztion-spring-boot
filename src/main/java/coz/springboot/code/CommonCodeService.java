package coz.springboot.code;

import com.github.pagehelper.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import coz.springboot.code.domain.CommonCode;
import coz.springboot.code.domain.CommonCodeSearch;

@Service
@RequiredArgsConstructor
class CommonCodeService {

    private final CommonCodeRepository commonCodeRepository;

    @Transactional(readOnly = true)
    public Page<CommonCode> searchCommonCodes(CommonCodeSearch commonCodeSearch) {
        commonCodeSearch.validatePageable();
        return commonCodeRepository.findCommonCodes(commonCodeSearch);
    }
}
