package com.ymdx.pay.demo.controller;

import com.ymdx.pay.demo.constant.PayType;
import com.ymdx.pay.demo.mq.RabbitMqTemplate;
import com.ymdx.pay.demo.mq.dto.BaseData;
import com.ymdx.pay.demo.mq.dto.PayData;
import com.ymdx.pay.demo.utils.GeneratOrderID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2021/1/27
 * @Description 京东代扣支付测试
 */
@RequestMapping(value = "/pay-server/v1.0/test")
@RestController
public class PayController {

    @Autowired
    private RabbitMqTemplate rabbitMqTemplate;

    @RequestMapping(value = "/jdWithholdBind", method = RequestMethod.GET)
    public String jdWithholdBind(){

        return "绑定成功";
    }

    @RequestMapping(value = "/jdWithholdPay", method = RequestMethod.GET)
    public String jdWithholdPay(){
        /**
         {
          "tenantId":"89772020",
          "orgId":"897720200001",
          "userId":"1323190234533572161",
          "userOrderNo":"2020012720021512678",
          "businessTypeId":1,
          "electCard":"595940303954959493",
          "orderAmount":1,
          "realAmount":1,
          "serviceId":"passenger.server.name",
          "routeKey":"passenger.withhold.pay.notify",
          "requestTime":"2020-02-01 12:23:44"
         }
         */
        String userOrderNo = GeneratOrderID.getGeneratID();
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String msg = "{\"businessTypeId\":2,\"electCard\":\"20208799000000011522\",\"orderAmount\":90,\"orgId\":\"897720200002\",\"realAmount\":2,\"requestTime\":\"2021-02-25 14:37:40\",\"routeKey\":\"pay.withhold.notify.routing_key\",\"serviceId\":\"ymdx-ymx-qr-bus\",\"settingKey\":\"89772020:897720200002\",\"tenantId\":\"89772020\",\"userId\":\"1341593873677840386\",\"userOrderNo\":\"202102251437409861364826883685900290\"}";
       // String msg = "{\"requestData\":{\"businessTypeId\":2,\"orderAmount\":160,\"orgId\":\"897720200002\",\"realAmount\":160,\"requestTime\":\"2021-02-04 18:09:23\",\"routeKey\":\"pay.withhold.notify.routing_key\",\"serviceId\":\"ymdx-ymx-qr-bus\",\"tenantId\":\"89772020\",\"userId\":\"1341593873677840386\",\"userOrderNo\":"+userOrderNo+"}}";
        rabbitMqTemplate.send(msg, "passenger.withhold.pay");
        return "代扣支付消息已发送，订单内容为【"+msg+"】";
    }
    //批量测试代扣支付
    @RequestMapping(value = "/jdWithholdPayBatch/{orderCount}", method = RequestMethod.GET)
    public String jdWithholdPay(@PathVariable("orderCount") Integer batchOrderCount) throws InterruptedException {
        StringBuffer buffer = new StringBuffer();
        for(int i = 0 ; i < batchOrderCount ; i++){
            String orderId = GeneratOrderID.getGeneratID();
            String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String msg = "{\"tenantId\":\"89772020\",\"orgId\":\"897720200002\",\"userId\":\"1341593873677840386\",\"userOrderNo\":"+orderId+",\"businessTypeId\":1,\"electCard\":\"595940303954959493\",\"orderAmount\":1,\"realAmount\":1,\"serviceId\":\"passenger.server.name\",\"routeKey\":\"passenger.withhold.pay.notify\",\"requestTime\":\""+currentTime+"\"}";
            rabbitMqTemplate.send(msg, "passenger.withhold.pay");
            buffer.append(msg).append("/\n/g");
            Thread.sleep(500);
        }
        return "随机生成订单号，批量发送代扣支付消息，所有订单内容为【"+buffer+"】";
    }

    /**
     * @author YaoShuangQi
     * @create 2021/1/28 16:13
     * @version 1.0.0
     * @Description 退款测试
        {
        "tenantId":"89772020",
        "orgId":"897720200001",
        "userId":"1323190234533572161",
        "userOrderNo":"2020012720021512678",
        "businessTypeId":1,
        "refundOrderNo":"退款原订单号",
        "refundNote":"测试代扣退款备注",
        "realAmount":1,
        "serviceId":"passenger.server.name",
        "routeKey":"passenger.withhold.refund.notify",
        "requestTime":"2020-02-01 15:53:44"
        }
     */
    @RequestMapping(value = "/jdWithholdReFund/{orderId}", method = RequestMethod.GET)
    public String jdWithholdReFund(@PathVariable String orderId){
        String userOrderNo = GeneratOrderID.getGeneratID();
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String msg = "{\"tenantId\":\"89772020\",\"orgId\":\"897720200002\",\"userId\":\"1341593873677840386\",\"userOrderNo\":"+userOrderNo+",\"businessTypeId\":1,\"refundOrderNo\":"+orderId+",\"refundNote\":\"测试代扣退款备注\",\"realAmount\":1,\"serviceId\":\"passenger.server.name\",\"routeKey\":\"passenger.withhold.refund.notify\",\"requestTime\":\""+currentTime+"\"}";
        rabbitMqTemplate.send(msg, "passenger.withhold.refund");
        return "退款消息已发出,退款内容为【"+msg+"】";
    }

//===============================================支付宝免密支付==========================================================

    //批量支付宝免密支付
    @RequestMapping(value = "/aliWithholdPayBatch/{orderCount}", method = RequestMethod.GET)
    public String aliWithholdPay(@PathVariable("orderCount") Integer batchOrderCount) throws InterruptedException {
        StringBuffer buffer = new StringBuffer();
        for(int i = 0 ; i < batchOrderCount ; i++){
            String orderId = GeneratOrderID.getGeneratID();
            String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String msg = "{\"tenantId\":\"89772020\",\"orgId\":\"897720200002\",\"userId\":\"0000000000001006270\",\"userOrderNo\":"+orderId+",\"businessTypeId\":1,\"electCard\":\"595940303954959493\",\"orderAmount\":1,\"realAmount\":1,\"serviceId\":\"passenger.server.name\",\"routeKey\":\"passenger.withhold.pay.notify\",\"requestTime\":\""+currentTime+"\"}";
            rabbitMqTemplate.send(msg, "passenger.withhold.pay");
            buffer.append(msg).append("/\n/g");
            Thread.sleep(500);
        }
        return "随机生成订单号，批量发送代扣支付消息，所有订单内容为【"+buffer+"】";
    }
    //支付宝代扣退款
    @RequestMapping(value = "/aliWithholdReFund/{orderId}", method = RequestMethod.GET)
    public String aliWithholdReFund(@PathVariable String orderId){
        String userOrderNo = GeneratOrderID.getGeneratID();
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String msg = "{\"tenantId\":\"89772020\",\"orgId\":\"897720200002\",\"userId\":\"0000000000001006270\",\"userOrderNo\":"+userOrderNo+",\"businessTypeId\":1,\"refundOrderNo\":"+orderId+",\"refundNote\":\"测试代扣退款备注\",\"realAmount\":1,\"serviceId\":\"passenger.server.name\",\"routeKey\":\"passenger.withhold.refund.notify\",\"requestTime\":\""+currentTime+"\"}";
        rabbitMqTemplate.send(msg, "passenger.withhold.refund");
        return "退款消息已发出,退款内容为【"+msg+"】";
    }
}
