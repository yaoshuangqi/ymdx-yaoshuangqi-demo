package com.ymdx.redis.config;

import org.springframework.stereotype.Component;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2022/1/13 10:57
 * @Version 1.0
 */
public class BusinessHandle {

    public void handleMessage(String message){
        System.out.println("当redis缓存中test-key值发生变化时，触发此方法");
        System.out.printf("message: [%s]%n", message);
    }
}
