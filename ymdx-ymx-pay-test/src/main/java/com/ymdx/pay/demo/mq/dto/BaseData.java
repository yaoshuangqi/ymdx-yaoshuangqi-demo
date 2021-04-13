package com.ymdx.pay.demo.mq.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-ymx-pay
 * @date 2021/1/22
 * @Description 请求信息基类
 */
@Data
@Accessors(chain = true)
public class BaseData implements Serializable {

    private String tenantId;    //租户ID
    private String orgId;       //机构ID
    private String userId;      //用户ID
    private String userOrderNo; //业务订单号（当前支付订单号）

    private String businessTypeId;  //支付业务类型id

    private Long realAmount;  //实际金额（单位分）


    /**构造配置条件所需的key*/
    public String getSettingKey(){
        return tenantId + ":" + orgId;
    }
}
