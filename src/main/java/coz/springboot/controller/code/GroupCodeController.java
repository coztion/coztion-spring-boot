package coz.springboot.controller.code;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import coz.springboot.dto.code.GroupCodeSearchParam;
import coz.springboot.dto.common.RestResponse;
import coz.springboot.service.code.GroupCodeService;
import coz.springboot.vo.code.GroupCode;

@Tag(name = "Group Code", description = "Group Code API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/group-codes")
public class GroupCodeController {

    private final GroupCodeService groupCodeService;

    @GetMapping
    public RestResponse<List<GroupCode>> getGroupCodes(GroupCodeSearchParam request) {
        List<GroupCode> groupCodes = groupCodeService.getGroupCodes(request);
        return RestResponse.ofSuccess(groupCodes);
    }
}
