package com.ymdx.kafka.demo;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2020/12/22
 * @Description
 */
@Component
public class KafkaConsumer {

    //消息监听
    @KafkaListener(topics = {"topic1"})
    public void onMessage1(ConsumerRecord<?, ?> record, Acknowledgment ack, Consumer consumer){
        // 消费的哪个topic、partition的消息,打印出消息内容
        long offset = record.offset();
        System.out.println("进行消费：topic="+record.topic()+"-分区="+record.partition()+"-消息="+record.value()+"-offset="+offset);
        //同时，这个可以采用一个线程池来执行任务
        System.out.println("offset="+record.offset());
        //根据配置，ack-mode: MANUAL_IMMEDIATE，需要手动进行消费确认
        ack.acknowledge();
    }
}
