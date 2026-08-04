package com.coztion.user;

import java.util.Optional;

import com.coztion.user.model.User;
import com.coztion.user.model.UserSearch;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Optional<User> getUser(UserSearch userSearch) {
        return userRepository.selectUser(userSearch);
    }
}
