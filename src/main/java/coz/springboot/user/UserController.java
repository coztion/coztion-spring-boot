package coz.springboot.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

import coz.springboot.common.dto.RestResponse;
import coz.springboot.common.helper.SessionHolder;
import coz.springboot.user.dto.UserInfoResponse;
import coz.springboot.user.model.SessionUser;

@Tag(name = "User", description = "User API")
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/info")
    public RestResponse<UserInfoResponse> getUserInfo() {
        SessionUser sessionUser = SessionHolder.getSessionUser();
        UserInfoResponse userInfo = UserInfoResponse.fromSessionUser(sessionUser);
        return RestResponse.ofSuccess(userInfo);
    }
}
