package com.example.demo.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {


    @Value("${redis.jwt.token.ttl}")
    private int ttl;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value, 30, TimeUnit.DAYS);
    }

    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}