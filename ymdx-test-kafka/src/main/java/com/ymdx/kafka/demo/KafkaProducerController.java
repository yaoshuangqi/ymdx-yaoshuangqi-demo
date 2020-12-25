package com.ymdx.kafka.demo;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2020/12/22
 * @Description
 */
@RestController
public class KafkaProducerController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping("/kafka")
    public String testKafka(){
        kafkaTemplate.send("topic1", "推送一条消息到卡夫卡"+ UUID.randomUUID().toString());
        return "推送成功到kakfa";
    }
}
