package com.ymdx.rabbitmq.controller;

import com.ymdx.rabbitmq.producer.MessageProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2021/1/14
 * @Description
 */
@RestController
public class TestController {

    @Autowired
    private MessageProducer messageProducer;

    @RequestMapping(value = "/rabbitmq1", method = RequestMethod.GET)
    public String test1(){
        messageProducer.send("===>支付消息"+ UUID.randomUUID().toString());
        return "消息发送成功";
    }
    @RequestMapping(value = "/rabbitmq2", method = RequestMethod.GET)
    public String test2(){
        messageProducer.send("不存在的routingKey", "zhz.test");

        return "已发送了一个不存在的路由routeKey队列，看看是否执行回调";
    }
}
