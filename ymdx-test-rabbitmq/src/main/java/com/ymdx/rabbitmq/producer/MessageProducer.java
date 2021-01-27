package com.ymdx.rabbitmq.producer;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2021/1/14
 * @Description
 */
@Component
public class MessageProducer implements InitializingBean {

    //自动注入RabbitTemplate模板类
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //确认消息是否正确发送到交换机回调（内部做区分处理，使用ack布尔判断）
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {

            System.out.println("==> 发送交换机时触发回调，消息id="+correlationData+",是否ack确认："+ack);
            if(!ack){
                //可以进行日志记录、异常处理、补偿处理等
                System.out.println("异常处理....:"+cause);
            }else {
                //更新数据库，可靠性投递机制
                System.out.println("==> 成功投递消息，进入业务处理模块...");
            }
        }
    };

    //消息由交换机发送到指定队列，消息的失败回调处理触发
    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText,
                                    String exchange, String routingKey) {
            System.out.println("==>消息从交换机发送到指定队列失败，触发回调函数做补偿机制处理");
            System.out.println("return exchange: " + exchange + ", routingKey: "
                    + routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);
        }
    };

    //发送消息方法调用: 构建Message消息
    public void send(Object message, Map<String, Object> properties) throws Exception {
        MessageHeaders mhs = new MessageHeaders(properties);
        Message msg = MessageBuilder.createMessage(message, mhs);

        //id + 时间戳 全局唯一  用于ack保证唯一一条消息,这边做测试写死一个。但是在做补偿策略的时候，必须保证这是全局唯一的消息
        CorrelationData correlationData = new CorrelationData("123456733890");
        rabbitTemplate.convertAndSend("exchange-1", "springboot.dde", msg, correlationData);
    }
    public void send(String msg, String routingKey) {
        rabbitTemplate.convertAndSend("exchange-1", routingKey, msg);
        System.out.println("===>> 带上routingKey参数。。。。");
    }
    public void send(String msg) {

        //发送消息到rabbitmq 用于同步刷新redis中的token信息
        String messageId = UUID.randomUUID().toString();
        //id + 时间戳 全局唯一
        CorrelationData correlationData = new CorrelationData(messageId);
        System.out.println("发送数据到交换机  {}  routeKey  {}   msg {} "+ "exchange-1"+"queue-1====》"+ msg);
        rabbitTemplate.convertAndSend("exchange-1", "ysq.test", msg, correlationData);

        System.out.println("数据发送完成" );

        //同步得到发送的结果 两个属性  一个为ack  一个为错误信息
        //这里只能判断 消息是否正常发送到交换机，无法保证消息是否正确写入队列
        try {
            CorrelationData.Confirm confirm = correlationData.getFuture().get(1, TimeUnit.SECONDS);
            System.out.println("数据发送完成 confirm : "+confirm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
    }
}
