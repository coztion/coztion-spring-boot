package coz.springboot.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import coz.springboot.repository.user.UserRepository;
import coz.springboot.vo.user.User;
import coz.springboot.vo.user.UserFilter;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public List<User> getUsers(UserFilter filter) {
        return userRepository.findUsersByFilter(filter);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> getUser(UserFilter filter) {
        return userRepository.findUserByFilter(filter);
    }
}
