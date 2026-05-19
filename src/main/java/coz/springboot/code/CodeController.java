package coz.springboot.code;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import coz.springboot.code.domain.Code;
import coz.springboot.code.domain.CommonCode;
import coz.springboot.code.domain.CommonCodeFilter;
import coz.springboot.common.dto.RestResponse;

@Tag(name = "Code", description = "Code API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/codes")
public class CodeController {

    private final CommonCodeService commonCodeService;

    @GetMapping
    public RestResponse<List<Code>> getCodes(@RequestParam List<String> groupCodes) {
        CommonCodeFilter commonCodeFilter = CommonCodeFilter.ofGroupCodesInUse(groupCodes);
        List<CommonCode> commonCodes = commonCodeService.getCommonCodes(commonCodeFilter);
        List<Code> codes = commonCodes.stream().map(CommonCode::toCode).toList();
        return RestResponse.ofSuccess(codes);
    }
}
