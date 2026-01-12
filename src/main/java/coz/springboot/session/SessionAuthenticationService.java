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
import coz.springboot.core.user.dto.UserSearchParam;
import coz.springboot.core.user.repository.UserRepository;
import coz.springboot.core.user.vo.SessionUser;
import coz.springboot.core.user.vo.User;
import coz.springboot.helper.MessageTranslator;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionAuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    private final MessageTranslator messageTranslator;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSearchParam param = UserSearchParam.ofUsername(username);
        Optional<User> optionalUser = userRepository.findUserByParam(param);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return SessionUser.ofUser(user);
        }

        log.error(messageTranslator.translate(Message.Exception.NOT_FOUND, Label.User.USER));
        throw new UsernameNotFoundException(messageTranslator.translate(Message.Exception.NOT_FOUND, Label.User.USER));
    }
}
