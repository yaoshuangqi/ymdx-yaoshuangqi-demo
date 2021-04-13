package com.ymdx.pay.demo.constant;

/**
 * @author YaoShuangQi
 * @create 2021/1/12 16:28
 * @version 1.0.0
 * @Description 支付操作类型枚举
 */
public enum PayType {

    /** 刷新支付配置*/
    refresh_config("[刷新支付配置]"),
    /** 代扣渠道绑定*/
    withhold_bind("[代扣渠道绑定]"),
    /** 代扣渠道解绑*/
    withhold_unbind("[代扣渠道解绑]"),
    /** 代扣支付*/
    withhold_pay("[代扣支付]"),
    /** 代扣退款*/
    withhold_refund("[代扣退款]"),
    /** 代扣支付回调查询*/
    withhold_callback("[代扣支付通知回调查询]"),
    /** 代扣退款回调查询*/
    withhold_refund_callback("[代扣退款通知回调查询]");

    private String message;
    PayType(String message){
        this.message = message;
    }
    /**
    * @Description: 获取枚举值
    * @Author: YaoShuangQi
    * @Date: 2020/9/7 11:55
    * @Param: [name] 枚举名称
    * @Return: java.lang.String
    */
    public String getMessage(String... name){
        if(name.length > 0){
            for(PayType type : PayType.values()){
                if(type.name().equals(name))
                    return  type.message;
            }
        }
        return this.message;
    }

}
