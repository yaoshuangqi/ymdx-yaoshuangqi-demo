package com.ymdx.pay.demo.mq.dto;

import lombok.Data;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-ymx-pay
 * @date 2021/1/22
 * @Description 代扣退款具体信息
 */
@Data
public class ReFundData extends BaseData {

    //退款原订单号
    private String refundOrderNo;
    //退款摘要
    private String refundNote;
}
