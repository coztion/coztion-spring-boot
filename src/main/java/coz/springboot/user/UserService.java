package coz.springboot.user;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import coz.springboot.user.domain.User;
import coz.springboot.user.domain.UserSearch;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Optional<User> getUser(UserSearch userSearch) {
        return userRepository.selectUser(userSearch);
    }
}
