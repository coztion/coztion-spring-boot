package coz.springboot.global.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import coz.springboot.global.constant.Label;
import coz.springboot.global.constant.Message;
import coz.springboot.global.helper.MessageTranslator;
import coz.springboot.service.user.UserService;
import coz.springboot.vo.user.SessionUser;
import coz.springboot.vo.user.User;
import coz.springboot.vo.user.UserFilter;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UserService userService;

    private final MessageTranslator messageTranslator;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserFilter userFilter = UserFilter.ofUsername(username);
        Optional<User> foundUser = userService.getUser(userFilter);

        if (foundUser.isPresent()) {
            User user = foundUser.get();
            return SessionUser.ofUser(user);
        }

        log.error(messageTranslator.translate(Message.Exception.NOT_FOUND, Label.User.USER));
        throw new UsernameNotFoundException(messageTranslator.translate(Message.Exception.NOT_FOUND, Label.User.USER));
    }
}
