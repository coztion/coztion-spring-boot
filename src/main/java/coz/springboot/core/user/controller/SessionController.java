package coz.springboot.core.user.controller;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import coz.springboot.dto.RestResponse;
import coz.springboot.helper.SessionHolder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class SessionController {

    @GetMapping
    public RestResponse<String> getSession() {
        String username = SessionHolder.getUsername();
        return RestResponse.ofSuccess(username);
    }

    @PutMapping("/refresh")
    public RestResponse<Boolean> refreshSession() {
        return RestResponse.ofSuccess(true);
    }
}
