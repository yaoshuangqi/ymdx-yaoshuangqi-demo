package com.ymdx.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.connection.PublisherCallbackChannel;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2021/1/14
 * @Description
 */
@Component
public class RabbitReceiver {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-1",
                    durable="true"),
            exchange = @Exchange(name = "exchange-1",
                    durable="true",
                    type= "topic",
                    ignoreDeclarationExceptions = "true"),
            key = {"ysq.*"}
    ))
    @RabbitHandler
    public void onMessage(Message message, Channel channel, MessageHeaders headers) throws Exception {
        System.err.println("消费端接受到消息-------------------------"+new String(message.getBody()));
        // 手动确认
        String correlationId = headers.get(PublisherCallbackChannel.RETURNED_MESSAGE_CORRELATION_KEY, String.class);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        System.err.println("消息唯一id："+deliveryTag);
        channel.basicReject(deliveryTag, false);
    }
}
