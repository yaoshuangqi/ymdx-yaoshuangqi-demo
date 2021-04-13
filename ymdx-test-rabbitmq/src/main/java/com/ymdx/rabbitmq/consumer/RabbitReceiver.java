package com.ymdx.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.ymdx.rabbitmq.exception.BusinessException;
import com.ymdx.ymx.pay.common.config.MqConfig;
import com.ymdx.ymx.pay.common.constant.JdCodeStatus;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.connection.PublisherCallbackChannel;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2021/1/14
 * @Description
 */
@Component
public class RabbitReceiver {

    @Autowired
    private RabbitProperties rabbitProperties;

    /*, arguments = {@Argument(name = "x-dead-letter-exchange", value = "DeadExchange"),
        @Argument(name = "x-dead-letter-routing-key", value = "DeadRouting")}*/
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-1",
                    durable="true"),
            exchange = @Exchange(name = "exchange-1",
                    durable="true"),
            key = {"ysq.test"}
    ), errorHandler = "deadErrorHandler")
    @RabbitHandler
    public void onMessage(Message message, Channel channel, MessageHeaders headers) throws Exception {

        //记录重试次数
        Integer retryCount = headers.get("retryCount", Integer.class);
        retryCount = Objects.isNull(retryCount) ? 1 : retryCount + 1;
        System.err.println("消费端接受到消息-------------------------"+new String(message.getBody()));
        message.getMessageProperties().setHeader("retryCount",retryCount);
        // 手动确认
        long  deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            String correlationId = headers.get(PublisherCallbackChannel.RETURNED_MESSAGE_CORRELATION_KEY, String.class);
            System.out.println("=====这个是的附加数据CorrelationData = " + correlationId);
            System.out.println("+====> 这个是消息头增加的消息：key1 = "+ headers.get("key1") + ",+++++= key2= " + headers.get("key2"));

            System.err.println("消息索引："+deliveryTag);

            //产生异常，导致订单处理失败

            int a = 1/0;

            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {

            System.out.println("=====================发生异常了，需要进行重试操作");
            //出现异常手动放回队列
            channel.basicNack(deliveryTag, false, false);
            //如果要进行重试机制，则需要抛出异常
            throw  new BusinessException();
        }
    }

    /**
     * 死信队列.
     *
     * @param message
     */

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "DeadQueue",
                    durable="true"),
            exchange = @Exchange(name = "DeadExchange",
                    durable="true"),
            key = {"DeadRouting"}
    ))
    @RabbitHandler
    public void dealSubscribe(Message message, Channel channel, MessageHeaders headers) throws IOException {
        System.out.println("已经进入死信队列，Dead Subscriber:" + new String(message.getBody(), "UTF-8"));
        System.out.println("获取异常描述：" + message.getMessageProperties().getHeader("customError"));;
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
