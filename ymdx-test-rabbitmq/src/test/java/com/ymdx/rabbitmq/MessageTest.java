package com.ymdx.rabbitmq;

import com.ymdx.rabbitmq.producer.MessageProducer;
import org.assertj.core.util.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2021/1/14
 * @Description
 */
@SpringBootTest(classes = RabbitmqApplication.class)
@RunWith(SpringRunner.class)
public class MessageTest {

    @Autowired
    private MessageProducer messageProducer;

    @Test
    public void testMQ() throws Exception{
        Map<String, Object> properties = new HashMap<>();
        properties.put("number", "12345");
        properties.put("send_time", new SimpleDateFormat().format(new Date()));
        //messageProducer.send("Hello RabbitMQ For Spring Boot!", properties);

        messageProducer.send("车是三想发打发第三方打分");
        Thread.sleep(10000);
    }

    @Test
    public void testDemo(){
        Map<Object, Object> objectObjectMap = new HashMap<>();

        objectObjectMap.put("key1", "测试1");

        System.out.println(objectObjectMap.toString());

        objectObjectMap.put("key1", "测试222");

        System.out.println(objectObjectMap.toString());
    }
}
