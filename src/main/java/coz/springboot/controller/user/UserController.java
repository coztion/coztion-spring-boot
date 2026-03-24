package coz.springboot.controller.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coz.springboot.dto.common.RestResponse;
import coz.springboot.dto.user.response.UserInfoResponse;
import coz.springboot.global.helper.SessionHolder;
import coz.springboot.vo.user.SessionUser;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/info")
    public RestResponse<UserInfoResponse> getUserInfo() {
        SessionUser sessionUser = SessionHolder.getSessionUser();
        UserInfoResponse userInfo = UserInfoResponse.ofSessionUser(sessionUser);
        return RestResponse.ofSuccess(userInfo);
    }
}
