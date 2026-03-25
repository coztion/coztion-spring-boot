package coz.springboot.code;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import coz.springboot.code.domain.CommonCode;
import coz.springboot.code.domain.CommonCodeFilter;
import coz.springboot.code.dto.CommonCodeGetRequest;
import coz.springboot.code.dto.CommonCodeResponse;
import coz.springboot.global.dto.response.RestResponse;

@Tag(name = "Common Code", description = "Common Code API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/common-codes")
public class CommonCodeController {

    private final CommonCodeService commonCodeService;

    @Operation(summary = "Get Common Codes", description = "Get a list of common codes")
    @GetMapping
    public RestResponse<List<CommonCodeResponse>> getCommonCodes(@ModelAttribute CommonCodeGetRequest request) {
        CommonCodeFilter commonCodeFilter = request.toDomain();
        List<CommonCode> commonCodes = commonCodeService.getCommonCodes(commonCodeFilter);
        List<CommonCodeResponse> response =
                commonCodes.stream().map(CommonCodeResponse::ofDomain).toList();
        return RestResponse.ofSuccess(response);
    }
}
