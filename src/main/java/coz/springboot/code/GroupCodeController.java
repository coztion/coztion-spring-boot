package coz.springboot.code;

import jakarta.validation.Valid;

import com.github.pagehelper.Page;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import coz.springboot.code.domain.GroupCode;
import coz.springboot.code.domain.GroupCodeFilter;
import coz.springboot.common.dto.PageResponse;
import coz.springboot.common.dto.RestResponse;

@Tag(name = "Group Code", description = "Group Code API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/group-codes")
public class GroupCodeController {

    private final GroupCodeService groupCodeService;

    @Operation(summary = "Get Group Codes", description = "Search group codes based on search filter")
    @GetMapping
    public RestResponse<PageResponse<GroupCode>> getGroupCodes(@Valid @ModelAttribute GroupCodeFilter groupCodeFilter) {
        Page<GroupCode> groupCodes = groupCodeService.getGroupCodes(groupCodeFilter);
        return RestResponse.ofSuccess(PageResponse.ofPage(groupCodes));
    }
}
