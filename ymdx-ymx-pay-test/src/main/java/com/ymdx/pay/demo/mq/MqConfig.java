package com.ymdx.pay.demo.mq;

/**
 * @author YaoShuangQi
 * @create 2021/1/8 17:10
 * @version 1.0.0
 * @Description 消息中间件配置信息
 */

public class MqConfig {

    //乘客服务支付交换机 队列名称
    //乘客服务支付交换机 队列名称
    public static final String WITHHOLD_PAY_QUEUE = "pay.withhold.pay.queue";
    public static final String WITHHOLD_PAY_EXCHANGE = "pay.withhold.pay.exchange";

    //支付回调通知交换机 队列名称
    public static final String PAY_NOTIFY_QUEUE_NAME = "pay.notify.queue";
    public static final String PAY_NOTIFY_EXCHANGE_NAME = "pay.notify.exchange";

}
