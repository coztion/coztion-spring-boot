package coz.springboot.core.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import io.swagger.v3.oas.annotations.servers.Server;

import lombok.RequiredArgsConstructor;

import coz.springboot.constant.Label;
import coz.springboot.constant.Message;
import coz.springboot.core.user.dto.UserSearchParam;
import coz.springboot.core.user.repository.UserRepository;
import coz.springboot.core.user.vo.User;
import coz.springboot.exception.ClientException;

@Server
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public List<User> getUsers(UserSearchParam command) {
        return userRepository.findUsersByParam(command);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUser(UserSearchParam command) {
        Optional<User> optionalUser = userRepository.findUserByParam(command);
        return optionalUser.orElseThrow(() -> new ClientException(Message.Exception.NOT_FOUND, Label.User.USER));
    }
}
