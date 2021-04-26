package com.xxcx.pay.controller;

import cn.hutool.core.util.IdUtil;
import com.xxcx.pay.mq.RabbitMqTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Random;

@RestController
public class PayServerController {

    @Autowired
    private RabbitMqTemplate rabbitMqTemplate;

    /**
     * 京东代扣支付测试
     * @param userOrderId
     * @return
     */
    @RequestMapping(value = "/jdDkPay", method = RequestMethod.GET)
    public String jdDKPay(@RequestParam String userOrderId){
        if(StringUtils.isEmpty(userOrderId)){
            userOrderId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + new Random().nextInt(9);
        }
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String message = "{\"tenantId\":\"89772020\",\"orgId\":\"897720200001\",\"userId\":\"1323190234533572161\",\"userOrderNo\":\""+userOrderId+"\",\"businessNo\":\"001\",\"electCard\":\"595940303954959493\",\"orderAmount\":1,\"realAmount\":1,\"serviceId\":\"passenger.server.name\",\"routeKey\":\"pay.withhold.notify.routing_key\",\"requestTime\":\""+currentTime+"\"}";

        rabbitMqTemplate.send(message, "passenger.withhold.pay","pay.withhold.pay.exchange");
        return "发送成功：" + message;
    }

}
