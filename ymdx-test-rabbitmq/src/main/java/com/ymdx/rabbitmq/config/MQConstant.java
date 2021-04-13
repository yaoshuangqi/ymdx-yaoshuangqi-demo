package com.ymdx.rabbitmq.config;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2021/3/29
 * @Description
 */
public class MQConstant {

    /** Exchange 交换器**/
     public static final String EXCHANGE_DELAY_FIX_NAME = "exchange.demo.fix.delay";
     public static final String EXCHANGE_DEAD_NAME = "exchange.demo.dead";
     /**路由定义 **/
     public static final String ROUTING_KEY_DELAY_FIX_1_NAME = "routingkey.demo.fix.1.delay";
     public static final String ROUTING_KEY_DEAD_NAME = "routingkey.demo.dead";
     /** 队列定义 **/
    public static final String QUEUE_DELAY_FIX_1_NAME = "queue.demo.fix.1.delay";
    public static final String QUEUE_DEAD_NAME = "queue.demo.dead";
}
