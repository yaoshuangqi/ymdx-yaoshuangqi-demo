package com.ymdx.rabbitmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2021/3/29
 * @Description
 */
@Slf4j
@Component
public class MessageDelayProducer implements InitializingBean {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String msg, String routingKey) {
        log.info("发送rabbitMQ延时消息:delay={}, msg={}", 10000, msg);
        rabbitTemplate.convertAndSend("normal-exchange", routingKey, msg, new MessagePostProcessor() {
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setDelay(10000);//毫秒
                return message;
            }
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.send("==>> 测试延迟消息是否进入死信队列中了", "normal-route-key");
    }
}
