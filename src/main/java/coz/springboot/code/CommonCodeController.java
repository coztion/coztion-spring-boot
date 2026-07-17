package coz.springboot.code;

import com.github.pagehelper.Page;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import coz.springboot.code.domain.CommonCode;
import coz.springboot.code.domain.CommonCodesSearch;
import coz.springboot.common.dto.PageResponse;
import coz.springboot.common.dto.RestResponse;

@Tag(name = "Common Code", description = "Common Code API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/common-codes")
public class CommonCodeController {

    private final CommonCodeService commonCodeService;

    @Operation(summary = "Get Common Codes")
    @GetMapping
    public RestResponse<PageResponse<CommonCode>> getCommonCodes(@ModelAttribute CommonCodesSearch commonCodesSearch) {
        Page<CommonCode> commonCodes = commonCodeService.getCommonCodes(commonCodesSearch);
        return RestResponse.ofSuccess(PageResponse.ofPage(commonCodes));
    }
}
