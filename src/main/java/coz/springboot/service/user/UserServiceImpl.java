package coz.springboot.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import coz.springboot.constant.Label;
import coz.springboot.constant.Message;
import coz.springboot.domain.user.User;
import coz.springboot.domain.user.UserFilter;
import coz.springboot.exception.ClientException;
import coz.springboot.repository.user.UserRepository;

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
    public User getUser(UserFilter filter) {
        Optional<User> optionalUser = userRepository.findUserByFilter(filter);
        return optionalUser.orElseThrow(() -> new ClientException(Message.Exception.NOT_FOUND, Label.User.USER));
    }
}
