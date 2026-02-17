package coz.springboot.session;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import coz.springboot.constant.Label;
import coz.springboot.constant.Message;
import coz.springboot.domain.user.SessionUser;
import coz.springboot.domain.user.User;
import coz.springboot.domain.user.UserFilter;
import coz.springboot.helper.MessageTranslator;
import coz.springboot.repository.user.UserRepository;
import coz.springboot.service.user.UserService;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionAuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserService userService;

    private final MessageTranslator messageTranslator;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserFilter userFilter = UserFilter.ofUsername(username);

        Optional<User> optionalUser = userRepository.findUserByFilter(userFilter);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return SessionUser.ofUser(user);
        }

        log.error(messageTranslator.translate(Message.Exception.NOT_FOUND, Label.User.USER));
        throw new UsernameNotFoundException(messageTranslator.translate(Message.Exception.NOT_FOUND, Label.User.USER));
    }
}
