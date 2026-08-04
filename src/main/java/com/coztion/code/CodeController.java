package com.coztion.code;

import java.util.List;

import com.coztion.code.model.Code;
import com.coztion.code.model.CommonCode;
import com.coztion.code.model.CommonCodesSearch;
import com.coztion.common.dto.RestResponse;
import com.github.pagehelper.Page;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

@Tag(name = "Code", description = "Code API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/codes")
public class CodeController {

    private final CommonCodeService commonCodeService;

    @GetMapping
    public RestResponse<List<Code>> getCodes(@RequestParam List<String> groupCodes) {
        CommonCodesSearch commonCodesSearch = CommonCodesSearch.ofEnabledGroupCodes(groupCodes);
        Page<CommonCode> commonCodes = commonCodeService.getCommonCodes(commonCodesSearch);
        List<Code> codes = commonCodes.stream().map(CommonCode::toCode).toList();
        return RestResponse.ofSuccess(codes);
    }
}
