package com.ymdx.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2020/12/22
 * @Description
 */
@SpringBootApplication
public class KafkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
        System.out.println("==> kafka demo is start running.....");
    }
}
