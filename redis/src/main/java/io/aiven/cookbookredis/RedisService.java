package io.aiven.cookbookredis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    public void simpleCommand() {
        redisTemplate.opsForValue().set("mykey", "myValue");
        System.out.println("Value of mykey: " + redisTemplate.opsForValue().get("mykey"));
    }

}
