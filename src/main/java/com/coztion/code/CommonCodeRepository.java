package com.coztion.code;

import com.coztion.code.model.CommonCode;
import com.coztion.code.model.CommonCodesSearch;
import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonCodeRepository {

    Page<CommonCode> selectCommonCodes(CommonCodesSearch commonCodesSearch);
}
