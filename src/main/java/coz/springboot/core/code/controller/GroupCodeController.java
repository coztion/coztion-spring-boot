package coz.springboot.core.code.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import coz.springboot.core.code.dto.GroupCodeSearchParam;
import coz.springboot.core.code.service.GroupCodeService;
import coz.springboot.core.code.vo.GroupCode;
import coz.springboot.dto.RestResponse;

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
