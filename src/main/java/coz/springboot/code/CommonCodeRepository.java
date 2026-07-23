package coz.springboot.code;

import com.github.pagehelper.Page;

import org.apache.ibatis.annotations.Mapper;

import coz.springboot.code.model.CommonCode;
import coz.springboot.code.model.CommonCodesSearch;

@Mapper
public interface CommonCodeRepository {

    Page<CommonCode> selectCommonCodes(CommonCodesSearch commonCodesSearch);
}
