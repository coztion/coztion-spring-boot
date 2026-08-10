package com.coztion.code;

import jakarta.validation.Valid;

import com.coztion.authority.model.AuthorityType;
import com.coztion.code.model.GroupCode;
import com.coztion.code.model.GroupCodeDelete;
import com.coztion.code.model.GroupCodeSave;
import com.coztion.code.model.GroupCodeSearch;
import com.coztion.code.model.GroupCodeUpdate;
import com.coztion.code.model.GroupCodesSearch;
import com.coztion.common.dto.PageResponse;
import com.coztion.common.dto.RestResponse;
import com.coztion.core.security.Authorize;
import com.coztion.menu.model.MenuId;
import com.coztion.user.model.SessionUser;
import com.github.pagehelper.Page;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

@Tag(name = "Group Code", description = "Group Code API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/group-codes")
public class GroupCodeController {

    private final GroupCodeService groupCodeService;

    @Operation(summary = "Get Group Codes")
    @GetMapping
    @Authorize(menuIds = MenuId.CODE_MANAGEMENT)
    public RestResponse<PageResponse<GroupCode>> getGroupCodes(
            @Valid @ModelAttribute GroupCodesSearch groupCodesSearch) {
        Page<GroupCode> groupCodes = groupCodeService.getGroupCodes(groupCodesSearch);
        return RestResponse.ofSuccess(PageResponse.ofPage(groupCodes));
    }

    @Operation(summary = "Get Group Code")
    @GetMapping("/{groupCode}")
    @Authorize(menuIds = MenuId.CODE_MANAGEMENT)
    public RestResponse<GroupCode> getGroupCode(
            @PathVariable String groupCode, @ModelAttribute GroupCodeSearch groupCodeSearch) {
        groupCodeSearch.setGroupCode(groupCode);
        GroupCode foundGroupCode = groupCodeService.getGroupCode(groupCodeSearch);
        return RestResponse.ofSuccess(foundGroupCode);
    }

    @Operation(summary = "Post Group Code")
    @PostMapping
    @Authorize(menuIds = MenuId.CODE_MANAGEMENT, authorityType = AuthorityType.WRITE)
    public RestResponse<GroupCode> postGroupCode(
            @Valid @RequestBody GroupCodeSave groupCodeSave, @AuthenticationPrincipal SessionUser sessionUser) {
        groupCodeSave.setAuthor(sessionUser.getUsername());
        GroupCode createdGroupCode = groupCodeService.saveGroupCode(groupCodeSave);
        return RestResponse.ofSuccess(createdGroupCode);
    }

    @Operation(summary = "Put Group Code")
    @PutMapping("/{groupCode}")
    @Authorize(menuIds = MenuId.CODE_MANAGEMENT, authorityType = AuthorityType.WRITE)
    public RestResponse<GroupCode> putGroupCode(
            @PathVariable String groupCode,
            @Valid @RequestBody GroupCodeUpdate groupCodeUpdate,
            @AuthenticationPrincipal SessionUser sessionUser) {
        groupCodeUpdate.setTargetGroupCode(groupCode);
        groupCodeUpdate.setAuthor(sessionUser.getUsername());
        GroupCode updatedGroupCode = groupCodeService.updateGroupCode(groupCodeUpdate);
        return RestResponse.ofSuccess(updatedGroupCode);
    }

    @Operation(summary = "Delete Group Code")
    @DeleteMapping("/{groupCode}")
    @Authorize(menuIds = MenuId.CODE_MANAGEMENT, authorityType = AuthorityType.WRITE)
    public RestResponse<Boolean> deleteGroupCode(
            @PathVariable String groupCode, @AuthenticationPrincipal SessionUser sessionUser) {
        GroupCodeDelete groupCodeDelete = GroupCodeDelete.builder()
                .targetGroupCode(groupCode)
                .author(sessionUser.getUsername())
                .build();
        groupCodeService.deleteGroupCode(groupCodeDelete);
        return RestResponse.ofSuccess(true);
    }
}
