package com.coztion.code;

import jakarta.validation.Valid;

import com.coztion.authority.model.AuthorityType;
import com.coztion.code.model.CommonCode;
import com.coztion.code.model.CommonCodeDelete;
import com.coztion.code.model.CommonCodeOperation;
import com.coztion.code.model.CommonCodeSave;
import com.coztion.code.model.CommonCodeSearch;
import com.coztion.code.model.CommonCodeUpdate;
import com.coztion.code.model.CommonCodesSearch;
import com.coztion.common.dto.PageResponse;
import com.coztion.common.dto.RestResponse;
import com.coztion.core.security.Authorize;
import com.coztion.menu.model.MenuId;
import com.coztion.user.model.SessionUser;
import com.github.pagehelper.Page;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

@Tag(name = "Common Code", description = "Common Code API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/common-codes")
public class CommonCodeController {

    private final CommonCodeService commonCodeService;

    @Operation(summary = "Get Common Codes")
    @GetMapping
    @Authorize(menuIds = MenuId.CODE_MANAGEMENT)
    public RestResponse<PageResponse<CommonCode>> getCommonCodes(@ModelAttribute CommonCodesSearch commonCodesSearch) {
        Page<CommonCode> commonCodes = commonCodeService.getCommonCodes(commonCodesSearch);
        return RestResponse.ofSuccess(PageResponse.ofPage(commonCodes));
    }

    @Operation(summary = "Get Common Code")
    @GetMapping("/{commonCode}")
    @Authorize(menuIds = MenuId.CODE_MANAGEMENT)
    public RestResponse<CommonCode> getCommonCode(@Valid @ModelAttribute CommonCodeSearch commonCodeSearch) {
        CommonCode foundCommonCode = commonCodeService.getCommonCode(commonCodeSearch);
        return RestResponse.ofSuccess(foundCommonCode);
    }

    @Operation(summary = "Post Common Code")
    @PostMapping
    @Authorize(menuIds = MenuId.CODE_MANAGEMENT, authorityType = AuthorityType.WRITE)
    public RestResponse<CommonCode> postCommonCode(
            @Valid @RequestBody CommonCodeSave commonCodeSave, @AuthenticationPrincipal SessionUser sessionUser) {
        commonCodeSave.setAuthor(sessionUser.getUsername());
        CommonCode createdCommonCode = commonCodeService.saveCommonCode(commonCodeSave);
        return RestResponse.ofSuccess(createdCommonCode);
    }

    @Operation(summary = "Put Common Code")
    @PutMapping("/{commonCode}")
    @Authorize(menuIds = MenuId.CODE_MANAGEMENT, authorityType = AuthorityType.WRITE)
    public RestResponse<CommonCode> putCommonCode(
            @Valid @RequestBody CommonCodeUpdate commonCodeUpdate, @AuthenticationPrincipal SessionUser sessionUser) {
        commonCodeUpdate.setAuthor(sessionUser.getUsername());
        CommonCode updatedCommonCode = commonCodeService.updateCommonCode(commonCodeUpdate);
        return RestResponse.ofSuccess(updatedCommonCode);
    }

    @Operation(summary = "Delete Common Code")
    @DeleteMapping("/{commonCode}")
    @Authorize(menuIds = MenuId.CODE_MANAGEMENT, authorityType = AuthorityType.WRITE)
    public RestResponse<Boolean> deleteCommonCode(
            @Valid @RequestBody CommonCodeDelete commonCodeDelete, @AuthenticationPrincipal SessionUser sessionUser) {
        commonCodeDelete.setAuthor(sessionUser.getUsername());
        commonCodeService.deleteCommonCode(commonCodeDelete);
        return RestResponse.ofSuccess(true);
    }

    @Operation(summary = "Operate Common Codes")
    @PostMapping("/operation")
    @Authorize(menuIds = MenuId.CODE_MANAGEMENT, authorityType = AuthorityType.WRITE)
    public RestResponse<Boolean> operationCommonCodes(
            @Valid @RequestBody CommonCodeOperation commonCodeOperation,
            @AuthenticationPrincipal SessionUser sessionUser) {
        commonCodeOperation.setAuthor(sessionUser.getUsername());
        commonCodeService.operateCommonCodes(commonCodeOperation);
        return RestResponse.ofSuccess(true);
    }
}
