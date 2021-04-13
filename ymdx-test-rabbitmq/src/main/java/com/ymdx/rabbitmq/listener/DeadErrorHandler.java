package com.ymdx.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Component;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2021/2/4
 * @Description
 */
@Component
public class DeadErrorHandler implements RabbitListenerErrorHandler {

    @Autowired
    private RabbitProperties rabbitProperties;

    @Override
    public Object handleError(Message amqpMessage, org.springframework.messaging.Message<?> message, ListenerExecutionFailedException exception) throws Exception {

        System.out.println("j进入到错误监听处理器中："+new String(amqpMessage.getBody()));
        amqpMessage.getMessageProperties().setHeader("customError", exception.getMessage());
        Integer retryCount = (Integer)amqpMessage.getMessageProperties().getHeader("retryCount");
        if(retryCount.intValue() == rabbitProperties.getListener().getSimple().getRetry().getMaxAttempts()){
            System.out.println("===》 已经达到最大重试次数，将进入发送短信，警告邮件，已经落盘到数据库中");
            return null;
        }
        return exception;
    }
}
