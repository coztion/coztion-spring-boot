package coz.springboot.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import coz.springboot.global.dto.RestResponse;
import coz.springboot.global.helper.SessionHolder;
import coz.springboot.user.dto.UserInfoResponse;
import coz.springboot.user.model.SessionUser;

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
