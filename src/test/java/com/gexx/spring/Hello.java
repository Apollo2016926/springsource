package com.gexx.spring;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.data.redis.core.RedisTemplate;

public class Hello {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(6666);
        RedisTemplate redisTemplate=new RedisTemplate();
        Redisson redisson = null;
        RLock lock = redisson.getLock("");
        lock.lock();

        redisTemplate.opsForValue().setIfAbsent("","");

    }

}


