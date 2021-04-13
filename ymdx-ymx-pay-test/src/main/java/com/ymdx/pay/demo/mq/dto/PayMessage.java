package com.ymdx.pay.demo.mq.dto;

import com.ymdx.pay.demo.constant.PayType;
import lombok.Data;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-ymx-pay
 * @date 2021/1/14
 * @Description 支付消息实体
 */
@Data
public class PayMessage {

    private String requestCode;   // 支付消息id（由乘客服务发送消息时生成,并保存为支付流水雪花id值）
    private BaseData requestData;   // 请求信息 （具体请求动作待定）
    private PayType payType;      // 支付类型

    private String routePath; //路由key

    public PayMessage(String requestCode, PayData payData, PayType payType) {
        this.requestCode = requestCode;
        this.requestData = payData;
        this.payType = payType;
    }

    public PayMessage(){}

}
