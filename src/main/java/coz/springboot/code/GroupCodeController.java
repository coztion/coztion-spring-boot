package coz.springboot.code;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import coz.springboot.code.dto.GroupCodeGetRequest;
import coz.springboot.code.dto.GroupCodeGetResponse;
import coz.springboot.code.model.GroupCode;
import coz.springboot.code.model.GroupCodeFilter;
import coz.springboot.global.dto.RestResponse;

@Tag(name = "Group Code", description = "Group Code API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/group-codes")
public class GroupCodeController {

    private final GroupCodeService groupCodeService;

    @Operation(summary = "Get Group Codes", description = "Search group codes based on search filter")
    @GetMapping
    public RestResponse<List<GroupCodeGetResponse>> getGroupCodes(GroupCodeGetRequest request) {
        GroupCodeFilter groupCodeFilter = request.toDomain();
        List<GroupCode> groupCodes = groupCodeService.getGroupCodes(groupCodeFilter);
        List<GroupCodeGetResponse> response =
                groupCodes.stream().map(GroupCodeGetResponse::ofDomain).toList();
        return RestResponse.ofSuccess(response);
    }
}
