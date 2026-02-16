package coz.springboot.core.code.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import coz.springboot.core.code.dto.CommonCodeSearchParam;
import coz.springboot.core.code.service.CommonCodeService;
import coz.springboot.core.code.vo.CommonCode;
import coz.springboot.dto.RestResponse;

@Tag(name = "Common Code", description = "Common Code API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/common-codes")
public class CommonCodeController {

    private final CommonCodeService commonCodeService;

    @Operation(summary = "Get Common Codes", description = "Get a list of common codes")
    @GetMapping
    public RestResponse<List<CommonCode>> getCommonCodes(@ModelAttribute CommonCodeSearchParam request) {
        List<CommonCode> commonCodes = commonCodeService.getCommonCodes(request);
        return RestResponse.ofSuccess(commonCodes);
    }
}
