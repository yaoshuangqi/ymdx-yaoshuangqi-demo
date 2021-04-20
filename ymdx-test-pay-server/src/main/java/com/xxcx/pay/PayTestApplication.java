package com.xxcx.pay;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PayTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayTestApplication.class, args);
        System.out.println("===>>> 支付服务测试demo已启动....");
    }
}
