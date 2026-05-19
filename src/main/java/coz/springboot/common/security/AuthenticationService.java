package coz.springboot.common.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import coz.springboot.common.constant.Label;
import coz.springboot.common.constant.Message;
import coz.springboot.common.helper.MessageTranslator;
import coz.springboot.user.UserService;
import coz.springboot.user.domain.SessionUser;
import coz.springboot.user.domain.User;
import coz.springboot.user.domain.UserFilter;

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
