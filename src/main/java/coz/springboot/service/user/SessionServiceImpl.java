package coz.springboot.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import coz.springboot.global.helper.RedisRepository;

@Service
@RequiredArgsConstructor
class SessionServiceImpl implements SessionService {

    private final RedisRepository redisRepository;

    @Transactional
    @Override
    public void logout(String username) {
        redisRepository.remove(username);
    }
}
