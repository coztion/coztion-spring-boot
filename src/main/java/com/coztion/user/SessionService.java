package com.coztion.user;

import com.coztion.common.cache.RedisRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class SessionService {

    private final RedisRepository redisRepository;

    @Transactional
    public void logout(String username) {
        redisRepository.delete(username);
    }
}
