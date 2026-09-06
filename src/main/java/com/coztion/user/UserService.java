package com.coztion.user;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.coztion.user.model.User;
import com.coztion.user.model.UserAuthoritiesSearch;
import com.coztion.user.model.UserAuthority;
import com.coztion.user.model.UserSearch;
import com.coztion.user.model.UsersSearch;
import com.github.pagehelper.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserAuthorityRepository userAuthorityRepository;

    @Transactional(readOnly = true)
    public Optional<User> getUser(UserSearch userSearch) {
        return userRepository.selectUser(userSearch);
    }

    @Transactional(readOnly = true)
    public Page<User> getUsers(UsersSearch usersSearch) {
        usersSearch.validatePageable();
        Page<User> users = userRepository.selectUsers(usersSearch);

        boolean hasUsers = !CollectionUtils.isEmpty(users);
        if (hasUsers && usersSearch.isIncludeAuthorityRoles()) {
            List<String> usernames = users.stream().map(User::getUsername).toList();
            List<UserAuthority> userAuthorities =
                    userAuthorityRepository.selectUserAuthorities(UserAuthoritiesSearch.ofUsernames(usernames));
            Map<String, List<String>> usernameAuthorityRoles = userAuthorities.stream()
                    .collect(Collectors.groupingBy(
                            UserAuthority::getUsername,
                            Collectors.mapping(UserAuthority::getAuthorityRole, Collectors.toList())));
            users.forEach(
                    user -> user.setAuthorityRoles(usernameAuthorityRoles.getOrDefault(user.getUsername(), List.of())));
        }

        return users;
    }
}
