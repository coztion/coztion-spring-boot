package coz.springboot.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import coz.springboot.user.domain.User;
import coz.springboot.user.domain.UserFilter;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> getUsers(UserFilter filter) {
        return userRepository.findUsersByFilter(filter);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUser(UserFilter filter) {
        return userRepository.findUserByFilter(filter);
    }
}
