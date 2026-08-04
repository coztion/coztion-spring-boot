package com.coztion.code;

import com.coztion.code.model.CommonCode;
import com.coztion.code.model.CommonCodesSearch;
import com.github.pagehelper.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class CommonCodeService {

    private final CommonCodeRepository commonCodeRepository;

    @Transactional(readOnly = true)
    public Page<CommonCode> getCommonCodes(CommonCodesSearch commonCodesSearch) {
        commonCodesSearch.validatePageable();
        return commonCodeRepository.selectCommonCodes(commonCodesSearch);
    }
}
