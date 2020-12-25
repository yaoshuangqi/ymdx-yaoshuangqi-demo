package com.ymdx.redisson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2020/12/24
 * @Description
 */
@SpringBootApplication
public class RedissonApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedissonApplication.class, args);
        System.out.println("==> redisson 分布式锁测试demo已启动....");
    }
}
