package coz.springboot.core.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import coz.springboot.user.UserService;
import coz.springboot.user.model.SessionUser;
import coz.springboot.user.model.User;
import coz.springboot.user.model.UserSearch;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserSearch userSearch = UserSearch.ofUsername(username);
        Optional<User> foundUser = userService.getUser(userSearch);

        if (foundUser.isPresent()) {
            User user = foundUser.get();
            return SessionUser.ofUser(user);
        }

        log.error("Username {} not found", username);
        throw new UsernameNotFoundException("Username " + username + " not found");
    }
}
