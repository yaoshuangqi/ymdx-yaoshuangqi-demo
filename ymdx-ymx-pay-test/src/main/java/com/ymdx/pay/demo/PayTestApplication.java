package com.ymdx.pay.demo;

import cn.hutool.core.date.DateUtil;
import com.ymdx.pay.demo.utils.SnowFlakeUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2021/1/27
 * @Description
 */
@SpringBootApplication
public class PayTestApplication {

    public static void main(String[] args) {

        SpringApplication.run(PayTestApplication.class, args);
        System.out.println("===>>> 支付服务测试接口已启动.....");

        String messageId = SnowFlakeUtils.getWorkerId();//IdWorker.getTimeId();
        System.out.println(messageId);

        LocalDateTime localDateTime = DateUtil.parseLocalDateTime("2021-02-02 09:36:16 942", "yyyy-MM-dd HH:mm:ss SSS");
        System.out.println(localDateTime);
    }
}
