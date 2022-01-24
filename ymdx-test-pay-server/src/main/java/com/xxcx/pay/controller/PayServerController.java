package com.xxcx.pay.controller;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONUtil;
import com.xxcx.pay.dto.AlarmMessageDto;
import com.xxcx.pay.mq.MRabbitMqTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.ActiveSpan;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author dell
 */
@Slf4j
@RestController
public class PayServerController {

    private List<AlarmMessageDto> list = new ArrayList<>();

    @Autowired
    private MRabbitMqTemplate rabbitMqTemplate;

    @GetMapping(value = "/getTraceId")
    public String getTraceId90(){
        ActiveSpan.error(new RuntimeException("自定义的一些异常，用于打印到sk日志中"));
        //将生成的traceId放入网关日志记录器中。


        ThreadUtil.execute(() -> {
            try {
                Thread.sleep(3000);
                log.info("异步执行一些任务");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        return "返回结果："+TraceContext.traceId();
    }

    @RequestMapping(value = "/alarm",method = RequestMethod.POST)
    public String alarm(@RequestBody List<AlarmMessageDto> alarmMessageList){
        AlarmMessageDto alarmMessageDto = new AlarmMessageDto();
        alarmMessageDto.setId0(1);
        alarmMessageDto.setName("test");
        alarmMessageDto.setStartTime(System.currentTimeMillis());
        log.info("===>>> 已收到警告消息：{}", alarmMessageList.toString());
        //具体处理告警信息,分别以不同形式进行传播，比如：邮箱，短信等
        list.addAll(alarmMessageList);
        return JSONUtil.toJsonStr(list.toString());
    }


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

    /**
     * @desc 支付宝转账
     * @author yaoShuangQi
     * @date 2021/6/29 10:36
     */
    @RequestMapping(value = "/aliTransfer", method = RequestMethod.GET)
    public String aliTransferPay(@RequestParam String userOrderId){
        if(StringUtils.isEmpty(userOrderId)){
            userOrderId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + new Random().nextInt(9);
        }
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String message = "{\"businessNo\":\"007\",\"channelCode\":\"aliPAY\",\"orgId\":\"897720200008\",\"realAmount\":11,\"requestTime\":\"2021-11-08 11:15:42\",\"routeKey\":\"balance.refund.notify\",\"serviceId\":\"whxd-zzcx-account\",\"settingKey\":\"89772020:897720200008\",\"tenantId\":\"89772020\",\"userId\":\"1454990349669814273\",\"userOrderNo\":\"20211108103459210463238\"}";

        rabbitMqTemplate.send(message, "online.transfer.routing_key","online.transfer.exchange");
        return "发送成功" + message;
    }

    /**
     * 支付宝代扣支付测试
     * @param userOrderId
     * @return
     */
    @RequestMapping(value = "/aliDkPay", method = RequestMethod.GET)
    public String aliDkPay(@RequestParam String userOrderId){
        if(StringUtils.isEmpty(userOrderId)){
            userOrderId = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + new Random().nextInt(9);
        }
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String message = "{\"tenantId\":\"89772020\",\"orgId\":\"897720200081\",\"userId\":\"1484409988296249345\",\"userOrderNo\":\""+userOrderId+"\",\"businessNo\":\"001\",\"electCard\":\"595940303954959493\",\"orderAmount\":1,\"realAmount\":1,\"serviceId\":\"passenger.server.name\",\"routeKey\":\"pay.withhold.notify.routing_key\",\"requestTime\":\""+currentTime+"\"}";

        rabbitMqTemplate.send(message, "passenger.withhold.pay","pay.withhold.pay.exchange");
        return "发送成功：" + message;
    }
}
