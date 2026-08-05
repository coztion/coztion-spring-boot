package com.coztion.code;

import java.util.Optional;

import com.coztion.code.model.CommonCode;
import com.coztion.code.model.CommonCodeSave;
import com.coztion.code.model.CommonCodeSearch;
import com.coztion.code.model.CommonCodesSearch;
import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonCodeRepository {

    Page<CommonCode> selectCommonCodes(CommonCodesSearch commonCodesSearch);

    Optional<CommonCode> selectCommonCode(CommonCodeSearch commonCodeSearch);

    int insertCommonCode(CommonCodeSave commonCodeSave);
}
