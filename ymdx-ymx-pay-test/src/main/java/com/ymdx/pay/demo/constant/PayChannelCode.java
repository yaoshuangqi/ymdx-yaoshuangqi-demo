package com.ymdx.pay.demo.constant;


/**
 * @author YaoShuangQi
 * @create 2021/1/12 14:18
 * @version 1.0.0
 * @Description 支付渠道代码
 */
public enum PayChannelCode {

    WITHHOLD_JDDK("jdDK", "京东代扣"),
    WITHHOLD_ALIDK("aliDK", "支付宝代扣"),
    WITHHOLD_NSDK("nsDk", "农商银行代扣");

    private String code;
    private String message;

    PayChannelCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String code(){
        return code;
    }

}
