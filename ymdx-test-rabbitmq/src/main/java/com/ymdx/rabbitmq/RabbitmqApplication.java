package com.ymdx.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2021/1/14
 * @Description
 */
@SpringBootApplication
public class RabbitmqApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(RabbitmqApplication.class, args);
        System.out.println("==>>> rabbitmq服务已启动...");

    }
}
