package com.ymdx.redis.controller;

import cn.hutool.core.lang.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2022/1/13 11:10
 * @Version 1.0
 */
@RestController
public class TestController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/update")
    public String test(){

        final String value = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set("test-key", value);
        return value;
    }
}
