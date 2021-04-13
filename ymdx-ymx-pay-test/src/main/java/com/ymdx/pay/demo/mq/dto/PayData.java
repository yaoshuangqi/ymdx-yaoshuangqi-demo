package com.ymdx.pay.demo.mq.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-ymx-pay
 * @date 2021/1/14
 * @Description 代扣请求具体信息
 * 说明：本实例为提供代扣支付业务
 */
@Data
@Accessors(chain = true)
public class PayData extends BaseData {

    private String electCard;//电子账号
    private Long orderAmount; //订单金额（单位分）

    private String payDiscountFlag;//是否存在优惠

    private String otherJson;   //其他字段 json字符串(扩展字段，暂未使用)

}
