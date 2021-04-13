package com.ymdx.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import static com.ymdx.ymx.pay.common.config.MqConfig.*;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2021/3/29
 * @Description
 */
@Component
public class MessageDelayReceiver {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "dead-queue-test",durable="true"),
            exchange = @Exchange(value = "dead-exchange-test"),
            key = {"dead-route-key"}
    ))
    @RabbitHandler
    public void onLinePayOrderDeadAndExpire(Message message, Channel channel, MessageHeaders headers) throws Exception {
        System.out.println("==>> 收到延迟消息："+new String(message.getBody()));
    }
}
