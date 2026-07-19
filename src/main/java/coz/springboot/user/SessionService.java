package coz.springboot.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import coz.springboot.common.redis.RedisRepository;

@Service
@RequiredArgsConstructor
class SessionService {

    private final RedisRepository redisRepository;

    @Transactional
    public void logout(String username) {
        redisRepository.delete(username);
    }
}
