package com.ymdx.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Desc 测试redis中键空间事件
 * 触发两种事件:
 * 1.keyspace 对key进行具体操作
 * 2.keyevent 影响key键名
 * @Author Mr.Yao
 * @Date 2022/1/13 10:31
 * @Version 1.0
 */
@SpringBootApplication
public class RedisApplication implements CommandLineRunner {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
        System.out.println("启动完成...");
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("------设置test-key");
        redisTemplate.opsForValue().set("test-key", "454545");
    }
}
